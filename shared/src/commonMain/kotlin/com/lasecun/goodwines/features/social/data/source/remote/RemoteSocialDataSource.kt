package com.lasecun.goodwines.features.social.data.source.remote

import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.social.data.source.remote.dto.FeedItemDto
import com.lasecun.goodwines.features.social.data.source.remote.dto.ReviewDto

/**
 * Remote contract for social and activity feed operations.
 */
interface RemoteSocialDataSource {
    suspend fun getActivityFeed(userId: String, page: Int = 0): NetworkResult<List<FeedItemDto>>
    suspend fun getReviewsForEntry(entryId: String): NetworkResult<List<ReviewDto>>
    suspend fun createReview(review: ReviewDto): NetworkResult<ReviewDto>
    suspend fun deleteReview(reviewId: String): NetworkResult<Unit>
    suspend fun likeEntry(entryId: String): NetworkResult<Unit>
    suspend fun unlikeEntry(entryId: String): NetworkResult<Unit>
}
