package com.lasecun.goodwines.features.wine.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines")
data class WineEntity(
    @PrimaryKey val id: String,
    val name: String,
    val winery: String,
    val vintage: Int?,
    val region: String,
    val country: String,
    val grapes: String,         // comma-separated list
    val style: String,          // WineStyle.name
    val imageUrl: String?,
    val averageRating: Float?
)
