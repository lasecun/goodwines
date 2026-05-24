package com.lasecun.goodwines.features.journal.data.source.local

import com.lasecun.goodwines.core.data.source.local.LocalTastingEntryDataSource
import com.lasecun.goodwines.features.journal.data.mapper.toDomain
import com.lasecun.goodwines.features.journal.data.mapper.toEntity
import com.lasecun.goodwines.features.journal.data.source.local.dao.TastingEntryDao
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry

class TastingEntryLocalDataSourceImpl(
    private val dao: TastingEntryDao
) : LocalTastingEntryDataSource {

    override suspend fun getEntriesByUser(userId: String): List<TastingEntry> =
        dao.getByUser(userId).map { it.toDomain() }

    override suspend fun getEntryById(id: String): TastingEntry? =
        dao.getById(id)?.toDomain()

    override suspend fun getDrafts(userId: String): List<TastingEntry> =
        dao.getDrafts(userId).map { it.toDomain() }

    override suspend fun getPendingSync(userId: String): List<TastingEntry> =
        dao.getPendingSync(userId).map { it.toDomain() }

    override suspend fun insertEntry(entry: TastingEntry) =
        dao.insert(entry.toEntity())

    override suspend fun updateSyncStatus(id: String, status: String, serverId: String?) =
        dao.updateSyncStatus(id, status, serverId)

    override suspend fun deleteEntry(id: String) =
        dao.deleteById(id)
}
