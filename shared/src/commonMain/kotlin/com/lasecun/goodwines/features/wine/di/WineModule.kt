package com.lasecun.goodwines.features.wine.di

import com.lasecun.goodwines.features.wine.data.repository.WineRepositoryImpl
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository
import org.koin.dsl.module

val wineModule = module {
    single<WineRepository> { WineRepositoryImpl(get()) }
}
