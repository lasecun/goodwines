package com.lasecun.goodwines.features.wine.data.repository

import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository

// Stub — replace with real local/remote sources in foundation-room-persistence (#4)
class WineRepositoryImpl : WineRepository {
    override suspend fun getWineById(id: String): Wine? = null
    override suspend fun searchWines(query: String): List<Wine> = emptyList()
    override suspend fun saveWine(wine: Wine) = Unit
}
