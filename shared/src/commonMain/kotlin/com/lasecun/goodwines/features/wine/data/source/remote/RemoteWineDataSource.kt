package com.lasecun.goodwines.features.wine.data.source.remote

import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.wine.data.source.remote.dto.WineDto
import com.lasecun.goodwines.features.wine.data.source.remote.dto.WineSearchResponseDto

/**
 * Remote contract for the wine catalog.
 * Implemented by a Supabase/HTTP client in the infra layer.
 */
interface RemoteWineDataSource {
    suspend fun searchWines(query: String, page: Int = 0, pageSize: Int = 20): NetworkResult<WineSearchResponseDto>
    suspend fun getWineById(id: String): NetworkResult<WineDto>
    suspend fun getWinesByRegion(region: String, page: Int = 0): NetworkResult<WineSearchResponseDto>
    suspend fun getTrendingWines(limit: Int = 20): NetworkResult<List<WineDto>>
}
