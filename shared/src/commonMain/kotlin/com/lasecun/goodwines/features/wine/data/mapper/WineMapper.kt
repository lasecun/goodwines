package com.lasecun.goodwines.features.wine.data.mapper

import com.lasecun.goodwines.features.wine.data.source.local.entity.WineEntity
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.model.WineStyle

fun WineEntity.toDomain(): Wine = Wine(
    id = id,
    name = name,
    winery = winery,
    vintage = vintage,
    region = region,
    country = country,
    grapes = grapes.split(",").filter { it.isNotEmpty() },
    style = runCatching { WineStyle.valueOf(style) }.getOrDefault(WineStyle.RED),
    imageUrl = imageUrl,
    averageRating = averageRating,
    abv = abv,
    description = description
)

fun Wine.toEntity(): WineEntity = WineEntity(
    id = id,
    name = name,
    winery = winery,
    vintage = vintage,
    region = region,
    country = country,
    grapes = grapes.joinToString(","),
    style = style.name,
    imageUrl = imageUrl,
    averageRating = averageRating,
    abv = abv,
    description = description
)
