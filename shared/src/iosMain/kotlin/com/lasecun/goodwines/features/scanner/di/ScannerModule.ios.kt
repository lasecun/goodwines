package com.lasecun.goodwines.features.scanner.di

import com.lasecun.goodwines.features.scanner.data.source.WineScannerDataSource
import com.lasecun.goodwines.features.scanner.data.source.WineScannerDataSourceImpl

actual fun createWineScannerDataSource(): WineScannerDataSource = WineScannerDataSourceImpl()
