package com.lasecun.goodwines.features.wine.domain.repository

import com.lasecun.goodwines.features.wine.domain.model.Wine

interface WineRepository {
    suspend fun getWineById(id: String): Wine?
    suspend fun searchWines(query: String): List<Wine>
    suspend fun saveWine(wine: Wine)
}
