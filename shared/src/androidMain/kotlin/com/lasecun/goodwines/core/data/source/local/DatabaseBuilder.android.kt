package com.lasecun.goodwines.core.data.source.local

import android.content.Context
import androidx.room.Room

actual class DatabaseBuilder(private val context: Context) {
    actual fun build(): GoodwinesDatabase {
        return Room.databaseBuilder<GoodwinesDatabase>(
            context = context,
            name = "goodwines.db"
        ).build()
    }
}
