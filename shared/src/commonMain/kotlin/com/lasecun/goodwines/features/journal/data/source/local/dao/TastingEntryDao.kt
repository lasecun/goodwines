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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: TastingEntryEntity)

    @Query("DELETE FROM tasting_entries WHERE id = :id")
    suspend fun deleteById(id: String)
}
