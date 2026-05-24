package com.lasecun.goodwines.features.wine.data.repository

import com.lasecun.goodwines.core.data.source.local.LocalWineDataSource
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository

class WineRepositoryImpl(
    private val localDataSource: LocalWineDataSource
) : WineRepository {
    override suspend fun getWineById(id: String): Wine? =
        localDataSource.getWineById(id)

    override suspend fun searchWines(query: String): List<Wine> =
        emptyList() // TODO: implement with local search + remote in later tasks

    override suspend fun saveWine(wine: Wine) =
        localDataSource.insertWine(wine)
}
