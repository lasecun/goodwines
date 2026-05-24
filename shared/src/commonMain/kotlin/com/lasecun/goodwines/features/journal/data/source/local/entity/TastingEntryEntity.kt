package com.lasecun.goodwines.features.journal.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores a tasting entry with a denormalized wine snapshot.
 * This preserves the wine as it was at tasting time, decoupled from the catalog.
 *
 * Sync fields:
 *  - [syncStatus] tracks whether this record needs to be pushed to the backend.
 *  - [localModifiedAt] is updated on every local write so the sync layer can order operations.
 *  - [serverId] is populated after a successful upload; may differ from the local [id] (UUID).
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
    val isPublic: Boolean,
    // Sync fields (added in DB version 2)
    val syncStatus: String = "SYNCED",   // SyncStatus.name
    val localModifiedAt: Long = 0L,
    val serverId: String? = null
)
