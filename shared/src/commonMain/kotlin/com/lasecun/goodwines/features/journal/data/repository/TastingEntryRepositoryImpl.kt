package com.lasecun.goodwines.features.journal.data.repository

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

// Stub — replace with real local/remote sources in foundation-room-persistence (#4)
class TastingEntryRepositoryImpl : TastingEntryRepository {
    override suspend fun getEntriesByUser(userId: String): List<TastingEntry> = emptyList()
    override suspend fun getEntryById(id: String): TastingEntry? = null
    override suspend fun saveEntry(entry: TastingEntry) = Unit
    override suspend fun deleteEntry(id: String) = Unit
}
