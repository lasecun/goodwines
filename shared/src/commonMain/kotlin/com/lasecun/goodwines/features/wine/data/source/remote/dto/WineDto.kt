package com.lasecun.goodwines.features.wine.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WineDto(
    val id: String,
    val name: String,
    val winery: String,
    val vintage: Int? = null,
    val region: String = "",
    val country: String = "",
    val grapes: List<String> = emptyList(),
    val style: String = "RED",
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("average_rating") val averageRating: Float? = null
)

@Serializable
data class WineSearchResponseDto(
    val wines: List<WineDto>,
    val total: Int,
    val page: Int,
    @SerialName("page_size") val pageSize: Int
)
