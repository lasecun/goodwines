package com.lasecun.goodwines.features.journal.domain.repository

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry

interface TastingEntryRepository {
    suspend fun getEntriesByUser(userId: String): List<TastingEntry>
    suspend fun getEntryById(id: String): TastingEntry?
    suspend fun saveEntry(entry: TastingEntry)
    suspend fun deleteEntry(id: String)
}
