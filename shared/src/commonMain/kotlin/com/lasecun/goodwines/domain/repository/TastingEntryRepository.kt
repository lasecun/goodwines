// Domain layer — repository contracts only. No implementation details here.
package com.lasecun.goodwines.domain.repository

import com.lasecun.goodwines.domain.model.TastingEntry

interface TastingEntryRepository {
    suspend fun getEntriesByUser(userId: String): List<TastingEntry>
    suspend fun getEntryById(id: String): TastingEntry?
    suspend fun saveEntry(entry: TastingEntry)
    suspend fun deleteEntry(id: String)
}
