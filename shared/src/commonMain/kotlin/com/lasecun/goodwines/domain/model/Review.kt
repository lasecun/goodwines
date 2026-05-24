// Domain layer — no framework dependencies allowed here.
// Dependency direction: domain ← data, domain ← presentation
package com.lasecun.goodwines.domain.model

data class Review(
    val id: String,
    val tastingEntryId: String,
    val authorId: String,
    val text: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long                     // epoch millis
)
