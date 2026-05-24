package com.lasecun.goodwines.features.social.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: String,
    @SerialName("tasting_entry_id") val tastingEntryId: String,
    @SerialName("author_id") val authorId: String,
    @SerialName("author_username") val authorUsername: String,
    @SerialName("author_avatar_url") val authorAvatarUrl: String? = null,
    val text: String,
    @SerialName("likes_count") val likesCount: Int = 0,
    @SerialName("comments_count") val commentsCount: Int = 0,
    @SerialName("created_at") val createdAt: Long
)

@Serializable
data class FeedItemDto(
    val id: String,
    val type: String,                      // "tasting_entry" | "review" | "follow"
    @SerialName("actor_id") val actorId: String,
    @SerialName("actor_username") val actorUsername: String,
    @SerialName("actor_avatar_url") val actorAvatarUrl: String? = null,
    @SerialName("tasting_entry") val tastingEntry: TastingEntryPreviewDto? = null,
    @SerialName("review") val review: ReviewDto? = null,
    @SerialName("created_at") val createdAt: Long
)

@Serializable
data class TastingEntryPreviewDto(
    val id: String,
    @SerialName("wine_name") val wineName: String,
    @SerialName("wine_winery") val wineWinery: String,
    @SerialName("wine_image_url") val wineImageUrl: String? = null,
    val rating: Float,
    val notes: String = ""
)
