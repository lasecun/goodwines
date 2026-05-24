package com.lasecun.goodwines.core.data.source.local

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseBuilder {
    actual fun build(): GoodwinesDatabase {
        return Room.databaseBuilder<GoodwinesDatabase>(
            name = documentDirectory() + "/goodwines.db"
        ).build()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val dir = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(dir?.path) { "Could not resolve iOS documents directory" }
    }
}
