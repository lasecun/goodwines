package com.lasecun.goodwines

import android.app.Application
import com.lasecun.goodwines.core.data.source.local.DatabaseBuilder
import com.lasecun.goodwines.core.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class GoodwinesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GoodwinesApplication)
            modules(
                appModules + module {
                    single { DatabaseBuilder(androidContext()) }
                }
            )
        }
    }
}
