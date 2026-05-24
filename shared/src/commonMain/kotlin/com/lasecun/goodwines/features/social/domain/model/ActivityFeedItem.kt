package com.lasecun.goodwines.features.social.domain.model

data class ActivityFeedItem(
    val id: String,
    val type: ActivityType,
    val actorUserId: String,
    val actorUsername: String,
    val actorAvatarUrl: String? = null,
    val wineName: String? = null,
    val wineWinery: String? = null,
    val rating: Float? = null,
    val text: String? = null,
    val createdAt: Long,
    val interactions: FeedInteractions = FeedInteractions()
)

enum class ActivityType {
    TASTING_ENTRY,
    REVIEW,
    FOLLOW
}

data class FeedInteractions(
    val likesCount: Int = 0,
    val commentsCount: Int = 0
)
