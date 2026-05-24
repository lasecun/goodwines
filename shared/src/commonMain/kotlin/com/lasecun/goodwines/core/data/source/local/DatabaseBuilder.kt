package com.lasecun.goodwines.core.data.source.local

// Platform-specific implementation builds the Room database for each target.
expect class DatabaseBuilder {
    fun build(): GoodwinesDatabase
}
