package com.lasecun.goodwines.core.di

import com.lasecun.goodwines.core.data.source.local.DatabaseBuilder
import com.lasecun.goodwines.core.data.source.local.LocalTastingEntryDataSource
import com.lasecun.goodwines.core.data.source.local.LocalWineDataSource
import com.lasecun.goodwines.features.journal.data.source.local.TastingEntryLocalDataSourceImpl
import com.lasecun.goodwines.features.wine.data.source.local.WineLocalDataSourceImpl
import org.koin.dsl.module

val coreModule = module {
    single { get<DatabaseBuilder>().build() }
    single { get<com.lasecun.goodwines.core.data.source.local.GoodwinesDatabase>().wineDao() }
    single { get<com.lasecun.goodwines.core.data.source.local.GoodwinesDatabase>().tastingEntryDao() }
    single { get<com.lasecun.goodwines.core.data.source.local.GoodwinesDatabase>().syncQueueDao() }
    single<LocalWineDataSource> { WineLocalDataSourceImpl(get()) }
    single<LocalTastingEntryDataSource> { TastingEntryLocalDataSourceImpl(get()) }
}
