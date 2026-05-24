package com.lasecun.goodwines.features.wine.data.repository

import com.lasecun.goodwines.core.data.source.local.LocalWineDataSource
import com.lasecun.goodwines.features.auth.data.source.local.LocalSessionDataSource
import com.lasecun.goodwines.features.wine.data.source.mock.MockWineDataSource
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository

class WineRepositoryImpl(
    private val localDataSource: LocalWineDataSource,
    private val localSessionDataSource: LocalSessionDataSource
) : WineRepository {
    override suspend fun getWineById(id: String): Wine? {
        val session = localSessionDataSource.getSession()
        return if (session?.isDemo == true) {
            MockWineDataSource.getWineById(id)
        } else {
            localDataSource.getWineById(id)
        }
    }

    override suspend fun searchWines(query: String): List<Wine> {
        val session = localSessionDataSource.getSession()
        return if (session?.isDemo == true) {
            MockWineDataSource.searchWines(query)
        } else {
            emptyList() // TODO: implement with local search + remote in later tasks
        }
    }

    override suspend fun saveWine(wine: Wine) =
        localDataSource.insertWine(wine)
}
