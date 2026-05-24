// Core data layer — local data source contracts.
// Implemented by Room in the foundation-room-persistence task.
package com.lasecun.goodwines.core.data.source.local

import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry

interface LocalWineDataSource {
    suspend fun getWineById(id: String): Wine?
    suspend fun insertWine(wine: Wine)
}

interface LocalTastingEntryDataSource {
    suspend fun getEntriesByUser(userId: String): List<TastingEntry>
    suspend fun getEntryById(id: String): TastingEntry?
    suspend fun getDrafts(userId: String): List<TastingEntry>
    suspend fun getPendingSync(userId: String): List<TastingEntry>
    suspend fun insertEntry(entry: TastingEntry)
    suspend fun updateSyncStatus(id: String, status: String, serverId: String? = null)
    suspend fun deleteEntry(id: String)
}
