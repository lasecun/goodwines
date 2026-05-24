package com.lasecun.goodwines.features.journal.domain.repository

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry

interface TastingEntryRepository {
    suspend fun getEntriesByUser(userId: String): List<TastingEntry>
    suspend fun getEntryById(id: String): TastingEntry?
    /** Save a committed entry — queued for backend sync immediately. */
    suspend fun saveEntry(entry: TastingEntry)
    /** Update an existing entry — queued for backend sync immediately. */
    suspend fun updateEntry(entry: TastingEntry)
    /** Save a draft locally — not synced until explicitly committed. */
    suspend fun saveDraft(entry: TastingEntry)
    suspend fun deleteEntry(id: String)
}
