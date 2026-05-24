package com.lasecun.goodwines.features.wine.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lasecun.goodwines.features.wine.data.source.local.entity.WineEntity

@Dao
interface WineDao {

    @Query("SELECT * FROM wines WHERE id = :id")
    suspend fun getById(id: String): WineEntity?

    @Query(
        "SELECT * FROM wines WHERE name LIKE '%' || :query || '%' " +
            "OR winery LIKE '%' || :query || '%'"
    )
    suspend fun search(query: String): List<WineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wine: WineEntity)

    @Query("DELETE FROM wines WHERE id = :id")
    suspend fun deleteById(id: String)
}
