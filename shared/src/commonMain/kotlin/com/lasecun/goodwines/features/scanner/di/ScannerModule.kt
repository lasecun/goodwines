package com.lasecun.goodwines.features.scanner.di

import com.lasecun.goodwines.features.scanner.data.source.WineScannerDataSource
import com.lasecun.goodwines.features.scanner.presentation.viewmodel.ScannerViewModel
import org.koin.dsl.module

expect fun createWineScannerDataSource(): WineScannerDataSource

val scannerModule = module {
    single<WineScannerDataSource> { createWineScannerDataSource() }
    factory { ScannerViewModel(get()) }
}
