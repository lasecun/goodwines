package com.lasecun.goodwines.core.data.sync

import com.lasecun.goodwines.core.data.network.getOrNull
import com.lasecun.goodwines.core.data.source.local.LocalTastingEntryDataSource
import com.lasecun.goodwines.core.domain.sync.SyncManager
import com.lasecun.goodwines.core.domain.sync.SyncResult
import com.lasecun.goodwines.core.platform.currentTimeMillis
import com.lasecun.goodwines.core.platform.randomUuid
import com.lasecun.goodwines.features.journal.data.mapper.toDto
import com.lasecun.goodwines.features.journal.data.source.local.dao.SyncQueueDao
import com.lasecun.goodwines.features.journal.data.source.local.entity.SyncQueueEntity
import com.lasecun.goodwines.features.journal.data.source.remote.RemoteJournalDataSource

class SyncManagerImpl(
    private val localEntryDataSource: LocalTastingEntryDataSource,
    private val remoteJournalDataSource: RemoteJournalDataSource,
    private val syncQueueDao: SyncQueueDao
) : SyncManager {

    companion object {
        private const val ENTITY_TASTING_ENTRY = "TASTING_ENTRY"
        private const val MAX_ATTEMPTS = 3
    }

    override suspend fun syncPendingEntries(userId: String): SyncResult {
        val pending = syncQueueDao.getByEntityType(ENTITY_TASTING_ENTRY)
        var synced = 0
        val errors = mutableListOf<String>()

        for (item in pending) {
            if (item.attempts >= MAX_ATTEMPTS) {
                errors.add("Max attempts reached for entry ${item.entityId}")
                continue
            }
            syncQueueDao.incrementAttempts(item.id, currentTimeMillis())
            val error = when (SyncOperation.valueOf(item.operation)) {
                SyncOperation.UPLOAD -> uploadEntry(item.entityId)
                SyncOperation.UPDATE -> updateEntry(item.entityId)
                SyncOperation.DELETE -> deleteEntry(item.entityId)
            }
            if (error == null) {
                syncQueueDao.remove(item.id)
                synced++
            } else {
                errors.add(error)
            }
        }

        return SyncResult(synced = synced, failed = errors.size, errors = errors)
    }

    override suspend fun enqueueEntry(entryId: String, operation: SyncOperation) {
        syncQueueDao.removeByEntityId(entryId)
        syncQueueDao.enqueue(
            SyncQueueEntity(
                id = randomUuid(),
                entityType = ENTITY_TASTING_ENTRY,
                entityId = entryId,
                operation = operation.name,
                createdAt = currentTimeMillis()
            )
        )
        val status = when (operation) {
            SyncOperation.UPLOAD -> SyncStatus.PENDING_UPLOAD
            SyncOperation.UPDATE -> SyncStatus.PENDING_UPDATE
            SyncOperation.DELETE -> SyncStatus.PENDING_DELETE
        }
        localEntryDataSource.updateSyncStatus(entryId, status.name)
    }

    private suspend fun uploadEntry(entryId: String): String? {
        val entry = localEntryDataSource.getEntryById(entryId)
            ?: return "Entry $entryId not found locally"
        val result = remoteJournalDataSource.createEntry(entry.toDto())
        return result.getOrNull()?.let {
            localEntryDataSource.updateSyncStatus(entryId, SyncStatus.SYNCED.name, it.id)
            null
        } ?: "Upload failed for entry $entryId"
    }

    private suspend fun updateEntry(entryId: String): String? {
        val entry = localEntryDataSource.getEntryById(entryId)
            ?: return "Entry $entryId not found locally"
        val result = remoteJournalDataSource.updateEntry(entry.toDto())
        return result.getOrNull()?.let {
            localEntryDataSource.updateSyncStatus(entryId, SyncStatus.SYNCED.name)
            null
        } ?: "Update failed for entry $entryId"
    }

    private suspend fun deleteEntry(entryId: String): String? {
        val result = remoteJournalDataSource.deleteEntry(entryId)
        return result.getOrNull()?.let {
            localEntryDataSource.deleteEntry(entryId)
            null
        } ?: "Delete failed for entry $entryId"
    }
}
