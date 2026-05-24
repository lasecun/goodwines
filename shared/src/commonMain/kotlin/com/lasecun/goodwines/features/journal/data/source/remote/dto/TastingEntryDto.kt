package com.lasecun.goodwines.features.journal.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TastingEntryDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    // Denormalised wine snapshot — mirrors local entity design
    @SerialName("wine_id") val wineId: String,
    @SerialName("wine_name") val wineName: String,
    @SerialName("wine_winery") val wineWinery: String,
    @SerialName("wine_vintage") val wineVintage: Int? = null,
    @SerialName("wine_region") val wineRegion: String = "",
    @SerialName("wine_country") val wineCountry: String = "",
    @SerialName("wine_style") val wineStyle: String = "RED",
    @SerialName("wine_image_url") val wineImageUrl: String? = null,
    val rating: Float,
    val notes: String = "",
    val date: Long,
    val location: String? = null,
    @SerialName("food_pairing") val foodPairing: String? = null,
    val mood: String? = null,
    @SerialName("is_public") val isPublic: Boolean = false
)

@Serializable
data class SyncEntriesRequestDto(
    @SerialName("entries") val entries: List<TastingEntryDto>,
    @SerialName("last_sync_at") val lastSyncAt: Long?
)

@Serializable
data class SyncEntriesResponseDto(
    @SerialName("updated_entries") val updatedEntries: List<TastingEntryDto>,
    @SerialName("deleted_ids") val deletedIds: List<String>,
    @SerialName("sync_timestamp") val syncTimestamp: Long
)
