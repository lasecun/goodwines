package com.lasecun.goodwines.features.wine.data.source.local

import com.lasecun.goodwines.core.data.source.local.LocalWineDataSource
import com.lasecun.goodwines.features.wine.data.mapper.toDomain
import com.lasecun.goodwines.features.wine.data.mapper.toEntity
import com.lasecun.goodwines.features.wine.data.source.local.dao.WineDao
import com.lasecun.goodwines.features.wine.domain.model.Wine

class WineLocalDataSourceImpl(private val dao: WineDao) : LocalWineDataSource {

    override suspend fun getWineById(id: String): Wine? =
        dao.getById(id)?.toDomain()

    override suspend fun insertWine(wine: Wine) =
        dao.insert(wine.toEntity())
}
