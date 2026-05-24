// Data layer — stub implementations. Replace with real Room/remote sources in later tasks.
// data → domain (inward dependency only)
package com.lasecun.goodwines.data.repository

import com.lasecun.goodwines.domain.model.Wine
import com.lasecun.goodwines.domain.repository.WineRepository

class WineRepositoryImpl : WineRepository {
    override suspend fun getWineById(id: String): Wine? = null
    override suspend fun searchWines(query: String): List<Wine> = emptyList()
    override suspend fun saveWine(wine: Wine) = Unit
}
