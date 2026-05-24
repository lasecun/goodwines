package com.lasecun.goodwines.features.wine.data.source.remote

import com.lasecun.goodwines.core.data.network.ApiException
import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.wine.data.source.remote.dto.WineDto
import com.lasecun.goodwines.features.wine.data.source.remote.dto.WineSearchResponseDto

/** Stub — replaced by real HTTP client in infra-backend-integration task. */
class RemoteWineDataSourceImpl : RemoteWineDataSource {
    private val notConfigured get() =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun searchWines(query: String, page: Int, pageSize: Int): NetworkResult<WineSearchResponseDto> = notConfigured
    override suspend fun getWineById(id: String): NetworkResult<WineDto> = notConfigured
    override suspend fun getWinesByRegion(region: String, page: Int): NetworkResult<WineSearchResponseDto> = notConfigured
    override suspend fun getTrendingWines(limit: Int): NetworkResult<List<WineDto>> = notConfigured
}
