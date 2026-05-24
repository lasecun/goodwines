package com.lasecun.goodwines.features.social.data.source.remote

import com.lasecun.goodwines.core.data.network.ApiException
import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.social.data.source.remote.dto.FeedItemDto
import com.lasecun.goodwines.features.social.data.source.remote.dto.ReviewDto

/** Stub — replaced by real HTTP client in infra-backend-integration task. */
class RemoteSocialDataSourceImpl : RemoteSocialDataSource {
    private val notConfigured get() =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun getActivityFeed(userId: String, page: Int): NetworkResult<List<FeedItemDto>> = notConfigured
    override suspend fun getReviewsForEntry(entryId: String): NetworkResult<List<ReviewDto>> = notConfigured
    override suspend fun createReview(review: ReviewDto): NetworkResult<ReviewDto> = notConfigured
    override suspend fun deleteReview(reviewId: String): NetworkResult<Unit> = notConfigured
    override suspend fun likeEntry(entryId: String): NetworkResult<Unit> = notConfigured
    override suspend fun unlikeEntry(entryId: String): NetworkResult<Unit> = notConfigured
}
