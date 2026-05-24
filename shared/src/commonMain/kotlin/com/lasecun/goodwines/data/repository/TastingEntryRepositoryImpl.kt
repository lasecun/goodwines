// Data layer — stub implementations. Replace with real Room/remote sources in later tasks.
// data → domain (inward dependency only)
package com.lasecun.goodwines.data.repository

import com.lasecun.goodwines.domain.model.TastingEntry
import com.lasecun.goodwines.domain.repository.TastingEntryRepository

class TastingEntryRepositoryImpl : TastingEntryRepository {
    override suspend fun getEntriesByUser(userId: String): List<TastingEntry> = emptyList()
    override suspend fun getEntryById(id: String): TastingEntry? = null
    override suspend fun saveEntry(entry: TastingEntry) = Unit
    override suspend fun deleteEntry(id: String) = Unit
}
