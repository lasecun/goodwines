// Data layer — local data source contract.
// Implemented by Room in the foundation-room-persistence task.
package com.lasecun.goodwines.data.source.local

import com.lasecun.goodwines.domain.model.TastingEntry
import com.lasecun.goodwines.domain.model.Wine

interface LocalWineDataSource {
    suspend fun getWineById(id: String): Wine?
    suspend fun insertWine(wine: Wine)
}

interface LocalTastingEntryDataSource {
    suspend fun getEntriesByUser(userId: String): List<TastingEntry>
    suspend fun insertEntry(entry: TastingEntry)
    suspend fun deleteEntry(id: String)
}
