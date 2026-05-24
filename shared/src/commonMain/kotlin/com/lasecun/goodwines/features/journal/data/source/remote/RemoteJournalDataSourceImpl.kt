package com.lasecun.goodwines.features.journal.data.source.remote

import com.lasecun.goodwines.core.data.network.ApiException
import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.journal.data.source.remote.dto.SyncEntriesRequestDto
import com.lasecun.goodwines.features.journal.data.source.remote.dto.SyncEntriesResponseDto
import com.lasecun.goodwines.features.journal.data.source.remote.dto.TastingEntryDto

/** Stub — replaced by real HTTP client in infra-backend-integration task. */
class RemoteJournalDataSourceImpl : RemoteJournalDataSource {
    private val notConfigured get() =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun getEntriesByUser(userId: String, page: Int): NetworkResult<List<TastingEntryDto>> = notConfigured
    override suspend fun getEntryById(id: String): NetworkResult<TastingEntryDto> = notConfigured
    override suspend fun createEntry(entry: TastingEntryDto): NetworkResult<TastingEntryDto> = notConfigured
    override suspend fun updateEntry(entry: TastingEntryDto): NetworkResult<TastingEntryDto> = notConfigured
    override suspend fun deleteEntry(id: String): NetworkResult<Unit> = notConfigured
    override suspend fun syncEntries(request: SyncEntriesRequestDto): NetworkResult<SyncEntriesResponseDto> = notConfigured
}
