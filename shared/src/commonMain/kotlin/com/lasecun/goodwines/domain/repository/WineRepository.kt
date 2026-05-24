// Domain layer — repository contracts only. No implementation details here.
package com.lasecun.goodwines.domain.repository

import com.lasecun.goodwines.domain.model.Wine

interface WineRepository {
    suspend fun getWineById(id: String): Wine?
    suspend fun searchWines(query: String): List<Wine>
    suspend fun saveWine(wine: Wine)
}
