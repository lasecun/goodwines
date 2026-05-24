package com.lasecun.goodwines.features.social.data.repository

import com.lasecun.goodwines.core.data.network.getOrNull
import com.lasecun.goodwines.features.social.data.source.remote.dto.FeedItemDto
import com.lasecun.goodwines.features.social.data.source.remote.RemoteSocialDataSource
import com.lasecun.goodwines.features.social.data.source.remote.dto.ReviewDto
import com.lasecun.goodwines.features.social.domain.model.ActivityFeedItem
import com.lasecun.goodwines.features.social.domain.model.ActivityType
import com.lasecun.goodwines.features.social.domain.model.FeedInteractions
import com.lasecun.goodwines.features.social.domain.model.Review
import com.lasecun.goodwines.features.social.domain.repository.SocialRepository

class SocialRepositoryImpl(
    private val remoteDataSource: RemoteSocialDataSource
) : SocialRepository {

    override suspend fun getActivityFeed(userId: String, page: Int): List<ActivityFeedItem> =
        remoteDataSource.getActivityFeed(userId, page)
            .getOrNull()
            ?.map { it.toDomain() }
            ?: emptyList()

    override suspend fun getReviewsForEntry(entryId: String): List<Review> =
        remoteDataSource.getReviewsForEntry(entryId)
            .getOrNull()
            ?.map { it.toDomain() }
            ?: emptyList()

    override suspend fun createReview(review: Review): Review {
        val dto = review.toDto()
        return remoteDataSource.createReview(dto).getOrNull()?.toDomain() ?: review
    }

    override suspend fun deleteReview(reviewId: String) {
        remoteDataSource.deleteReview(reviewId)
    }

    override suspend fun likeEntry(entryId: String) {
        remoteDataSource.likeEntry(entryId)
    }

    override suspend fun unlikeEntry(entryId: String) {
        remoteDataSource.unlikeEntry(entryId)
    }

    private fun ReviewDto.toDomain() = Review(
        id = id,
        tastingEntryId = tastingEntryId,
        authorId = authorId,
        text = text,
        likesCount = likesCount,
        commentsCount = commentsCount,
        createdAt = createdAt
    )

    private fun Review.toDto() = ReviewDto(
        id = id,
        tastingEntryId = tastingEntryId,
        authorId = authorId,
        authorUsername = "",
        text = text,
        likesCount = likesCount,
        commentsCount = commentsCount,
        createdAt = createdAt
    )

    private fun FeedItemDto.toDomain(): ActivityFeedItem {
        val mappedType = when (type.lowercase()) {
            "review" -> ActivityType.REVIEW
            "follow" -> ActivityType.FOLLOW
            else -> ActivityType.TASTING_ENTRY
        }

        return ActivityFeedItem(
            id = id,
            type = mappedType,
            actorUserId = actorId,
            actorUsername = actorUsername,
            actorAvatarUrl = actorAvatarUrl,
            wineName = tastingEntry?.wineName,
            wineWinery = tastingEntry?.wineWinery,
            rating = tastingEntry?.rating,
            text = review?.text ?: tastingEntry?.notes,
            createdAt = createdAt,
            interactions = FeedInteractions(
                likesCount = review?.likesCount ?: 0,
                commentsCount = review?.commentsCount ?: 0
            )
        )
    }
}
