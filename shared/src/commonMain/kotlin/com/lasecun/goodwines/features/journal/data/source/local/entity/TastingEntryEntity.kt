package com.lasecun.goodwines.features.journal.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores a tasting entry with a denormalized wine snapshot.
 * This preserves the wine as it was at tasting time, decoupled from the catalog.
 */
@Entity(tableName = "tasting_entries")
data class TastingEntryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val wineId: String,
    // Wine snapshot fields
    val wineName: String,
    val wineWinery: String,
    val wineVintage: Int?,
    val wineRegion: String,
    val wineCountry: String,
    val wineGrapes: String,     // comma-separated
    val wineStyle: String,      // WineStyle.name
    val wineImageUrl: String?,
    // Tasting fields
    val rating: Float,
    val notes: String,
    val date: Long,             // epoch millis
    val location: String?,
    val foodPairing: String?,
    val mood: String?,
    val isPublic: Boolean
)
