package com.lasecun.goodwines

import com.lasecun.goodwines.core.data.source.local.DatabaseBuilder
import com.lasecun.goodwines.core.di.appModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

// Called from iOSApp.swift before the first Compose frame.
fun initKoin() {
    startKoin {
        modules(
            appModules + module {
                single { DatabaseBuilder() }
            }
        )
    }
}
