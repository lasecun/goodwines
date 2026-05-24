package com.lasecun.goodwines.features.journal.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lasecun.goodwines.features.journal.data.source.local.entity.TastingEntryEntity

@Dao
interface TastingEntryDao {

    @Query("SELECT * FROM tasting_entries WHERE userId = :userId ORDER BY date DESC")
    suspend fun getByUser(userId: String): List<TastingEntryEntity>

    @Query("SELECT * FROM tasting_entries WHERE id = :id")
    suspend fun getById(id: String): TastingEntryEntity?

    @Query("SELECT * FROM tasting_entries WHERE userId = :userId AND syncStatus != 'SYNCED' ORDER BY localModifiedAt ASC")
    suspend fun getPendingSync(userId: String): List<TastingEntryEntity>

    @Query("SELECT * FROM tasting_entries WHERE syncStatus = 'DRAFT' AND userId = :userId ORDER BY localModifiedAt DESC")
    suspend fun getDrafts(userId: String): List<TastingEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: TastingEntryEntity)

    @Query("UPDATE tasting_entries SET syncStatus = :status, serverId = :serverId WHERE id = :id")
    suspend fun updateSyncStatus(id: String, status: String, serverId: String? = null)

    @Query("DELETE FROM tasting_entries WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM tasting_entries WHERE syncStatus = 'PENDING_DELETE'")
    suspend fun deletePendingDeletions()
}
