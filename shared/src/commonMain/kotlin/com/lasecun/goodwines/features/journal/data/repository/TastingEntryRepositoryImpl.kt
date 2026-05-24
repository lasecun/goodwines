package com.lasecun.goodwines.features.journal.data.repository

import com.lasecun.goodwines.core.data.source.local.LocalTastingEntryDataSource
import com.lasecun.goodwines.core.data.sync.SyncOperation
import com.lasecun.goodwines.core.data.sync.SyncStatus
import com.lasecun.goodwines.core.domain.sync.SyncManager
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

/**
 * Offline-first repository implementation.
 *
 * All writes go to local Room storage immediately so fast capture never waits for the network.
 * [SyncManager.enqueueEntry] is called after every local write so the background sync engine
 * can push changes to the backend when connectivity is available.
 */
class TastingEntryRepositoryImpl(
    private val localDataSource: LocalTastingEntryDataSource,
    private val syncManager: SyncManager
) : TastingEntryRepository {

    override suspend fun getEntriesByUser(userId: String): List<TastingEntry> =
        localDataSource.getEntriesByUser(userId)

    override suspend fun getEntryById(id: String): TastingEntry? =
        localDataSource.getEntryById(id)

    override suspend fun saveEntry(entry: TastingEntry) {
        // Write local first — never blocks on network
        localDataSource.insertEntry(entry)
        // Queue for backend sync
        syncManager.enqueueEntry(entry.id, SyncOperation.UPLOAD)
    }

    override suspend fun updateEntry(entry: TastingEntry) {
        localDataSource.insertEntry(entry) // REPLACE strategy handles updates
        syncManager.enqueueEntry(entry.id, SyncOperation.UPDATE)
    }

    override suspend fun saveDraft(entry: TastingEntry) {
        // Drafts are stored locally with DRAFT status — not queued for sync
        localDataSource.insertEntry(entry)
        // No enqueue: drafts sync only when explicitly committed by the user
    }

    override suspend fun deleteEntry(id: String) {
        // Mark as pending delete so sync engine can remove it from the backend first.
        // Physical row deletion happens after a successful remote delete.
        localDataSource.updateSyncStatus(id, SyncStatus.PENDING_DELETE.name)
        syncManager.enqueueEntry(id, SyncOperation.DELETE)
    }
}
