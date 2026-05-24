package com.lasecun.goodwines.features.journal.data.source.remote

import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.journal.data.source.remote.dto.SyncEntriesRequestDto
import com.lasecun.goodwines.features.journal.data.source.remote.dto.SyncEntriesResponseDto
import com.lasecun.goodwines.features.journal.data.source.remote.dto.TastingEntryDto

/**
 * Remote contract for journal sync.
 * The backend is the source of truth for public entries; local DB is the
 * primary store for drafts and offline-first capture.
 */
interface RemoteJournalDataSource {
    suspend fun getEntriesByUser(userId: String, page: Int = 0): NetworkResult<List<TastingEntryDto>>
    suspend fun getEntryById(id: String): NetworkResult<TastingEntryDto>
    suspend fun createEntry(entry: TastingEntryDto): NetworkResult<TastingEntryDto>
    suspend fun updateEntry(entry: TastingEntryDto): NetworkResult<TastingEntryDto>
    suspend fun deleteEntry(id: String): NetworkResult<Unit>
    /** Bidirectional sync: upload local changes, download server updates. */
    suspend fun syncEntries(request: SyncEntriesRequestDto): NetworkResult<SyncEntriesResponseDto>
}
