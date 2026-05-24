package com.lasecun.goodwines.features.journal.data.repository

import com.lasecun.goodwines.core.data.source.local.LocalTastingEntryDataSource
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class TastingEntryRepositoryImpl(
    private val localDataSource: LocalTastingEntryDataSource
) : TastingEntryRepository {
    override suspend fun getEntriesByUser(userId: String): List<TastingEntry> =
        localDataSource.getEntriesByUser(userId)

    override suspend fun getEntryById(id: String): TastingEntry? = null // TODO: add to LocalTastingEntryDataSource

    override suspend fun saveEntry(entry: TastingEntry) =
        localDataSource.insertEntry(entry)

    override suspend fun deleteEntry(id: String) =
        localDataSource.deleteEntry(id)
}
