package com.lasecun.goodwines.features.wine.di

import com.lasecun.goodwines.features.auth.data.source.local.LocalSessionDataSource
import com.lasecun.goodwines.features.wine.data.repository.WineRepositoryImpl
import com.lasecun.goodwines.features.wine.data.source.remote.RemoteWineDataSource
import com.lasecun.goodwines.features.wine.data.source.remote.RemoteWineDataSourceImpl
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository
import org.koin.dsl.module

val wineModule = module {
    single<RemoteWineDataSource> { RemoteWineDataSourceImpl() }
    single<WineRepository> { WineRepositoryImpl(get(), get<LocalSessionDataSource>()) }
}
