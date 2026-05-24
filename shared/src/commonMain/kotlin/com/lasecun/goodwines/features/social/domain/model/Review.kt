package com.lasecun.goodwines.features.social.domain.model

data class Review(
    val id: String,
    val tastingEntryId: String,
    val authorId: String,
    val text: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long                     // epoch millis
)
