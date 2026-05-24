package com.lasecun.goodwines.core.data.source.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.lasecun.goodwines.features.journal.data.source.local.dao.SyncQueueDao
import com.lasecun.goodwines.features.journal.data.source.local.dao.TastingEntryDao
import com.lasecun.goodwines.features.journal.data.source.local.entity.SyncQueueEntity
import com.lasecun.goodwines.features.journal.data.source.local.entity.TastingEntryEntity
import com.lasecun.goodwines.features.wine.data.source.local.dao.WineDao
import com.lasecun.goodwines.features.wine.data.source.local.entity.WineEntity

// Room generates the actual implementation via KSP for each platform.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object GoodwinesDatabaseConstructor : RoomDatabaseConstructor<GoodwinesDatabase> {
    override fun initialize(): GoodwinesDatabase
}

@Database(
    entities = [WineEntity::class, TastingEntryEntity::class, SyncQueueEntity::class],
    version = 2
)
@ConstructedBy(GoodwinesDatabaseConstructor::class)
abstract class GoodwinesDatabase : RoomDatabase() {
    abstract fun wineDao(): WineDao
    abstract fun tastingEntryDao(): TastingEntryDao
    abstract fun syncQueueDao(): SyncQueueDao
}
