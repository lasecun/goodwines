package com.lasecun.goodwines

import com.lasecun.goodwines.core.di.appModules
import org.koin.core.context.startKoin

// Called from iOSApp.swift before the first Compose frame.
fun initKoin() {
    startKoin {
        modules(appModules)
    }
}
