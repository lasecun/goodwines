package com.lasecun.goodwines

import android.app.Application
import com.lasecun.goodwines.core.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GoodwinesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GoodwinesApplication)
            modules(appModules)
        }
    }
}
