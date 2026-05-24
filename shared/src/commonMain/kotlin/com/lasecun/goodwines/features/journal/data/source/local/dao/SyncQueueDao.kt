package com.lasecun.goodwines.features.journal.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lasecun.goodwines.features.journal.data.source.local.entity.SyncQueueEntity

@Dao
interface SyncQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enqueue(item: SyncQueueEntity)

    @Query("SELECT * FROM sync_queue ORDER BY createdAt ASC")
    suspend fun getAll(): List<SyncQueueEntity>

    @Query("SELECT * FROM sync_queue WHERE entityType = :entityType ORDER BY createdAt ASC")
    suspend fun getByEntityType(entityType: String): List<SyncQueueEntity>

    @Query("DELETE FROM sync_queue WHERE id = :id")
    suspend fun remove(id: String)

    @Query("DELETE FROM sync_queue WHERE entityId = :entityId")
    suspend fun removeByEntityId(entityId: String)

    @Query("UPDATE sync_queue SET attempts = attempts + 1, lastAttemptAt = :now WHERE id = :id")
    suspend fun incrementAttempts(id: String, now: Long)

    @Query("SELECT COUNT(*) FROM sync_queue")
    suspend fun pendingCount(): Int
}
