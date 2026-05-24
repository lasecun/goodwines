package com.lasecun.goodwines.features.journal.data.mapper

import com.lasecun.goodwines.core.data.sync.SyncStatus
import com.lasecun.goodwines.core.platform.currentTimeMillis
import com.lasecun.goodwines.features.journal.data.source.local.entity.TastingEntryEntity
import com.lasecun.goodwines.features.journal.data.source.remote.dto.TastingEntryDto
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.model.WineStyle

fun TastingEntryEntity.toDomain(): TastingEntry = TastingEntry(
    id = id,
    userId = userId,
    wine = Wine(
        id = wineId,
        name = wineName,
        winery = wineWinery,
        vintage = wineVintage,
        region = wineRegion,
        country = wineCountry,
        grapes = wineGrapes.split(",").filter { it.isNotEmpty() },
        style = runCatching { WineStyle.valueOf(wineStyle) }.getOrDefault(WineStyle.RED),
        imageUrl = wineImageUrl
    ),
    rating = rating,
    notes = notes,
    date = date,
    location = location,
    foodPairing = foodPairing,
    mood = mood,
    isPublic = isPublic
)

fun TastingEntry.toEntity(
    syncStatus: SyncStatus = SyncStatus.PENDING_UPLOAD,
    localModifiedAt: Long = currentTimeMillis(),
    serverId: String? = null
): TastingEntryEntity = TastingEntryEntity(
    id = id,
    userId = userId,
    wineId = wine.id,
    wineName = wine.name,
    wineWinery = wine.winery,
    wineVintage = wine.vintage,
    wineRegion = wine.region,
    wineCountry = wine.country,
    wineGrapes = wine.grapes.joinToString(","),
    wineStyle = wine.style.name,
    wineImageUrl = wine.imageUrl,
    rating = rating,
    notes = notes,
    date = date,
    location = location,
    foodPairing = foodPairing,
    mood = mood,
    isPublic = isPublic,
    syncStatus = syncStatus.name,
    localModifiedAt = localModifiedAt,
    serverId = serverId
)

fun TastingEntry.toDto(): TastingEntryDto = TastingEntryDto(
    id = id,
    userId = userId,
    wineId = wine.id,
    wineName = wine.name,
    wineWinery = wine.winery,
    wineVintage = wine.vintage,
    wineRegion = wine.region,
    wineCountry = wine.country,
    wineStyle = wine.style.name,
    wineImageUrl = wine.imageUrl,
    rating = rating,
    notes = notes,
    date = date,
    location = location,
    foodPairing = foodPairing,
    mood = mood,
    isPublic = isPublic
)
