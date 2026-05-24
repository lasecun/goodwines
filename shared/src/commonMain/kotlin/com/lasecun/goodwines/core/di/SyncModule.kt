package com.lasecun.goodwines.core.di

import com.lasecun.goodwines.core.data.sync.SyncManagerImpl
import com.lasecun.goodwines.core.domain.sync.SyncManager
import org.koin.dsl.module

val syncModule = module {
    single<SyncManager> { SyncManagerImpl(get(), get(), get()) }
}
