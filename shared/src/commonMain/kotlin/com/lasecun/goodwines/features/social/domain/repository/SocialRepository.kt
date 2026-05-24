package com.lasecun.goodwines.features.social.domain.repository

import com.lasecun.goodwines.features.social.domain.model.Review

interface SocialRepository {
    suspend fun getActivityFeed(userId: String, page: Int = 0): List<Review>
    suspend fun getReviewsForEntry(entryId: String): List<Review>
    suspend fun createReview(review: Review): Review
    suspend fun deleteReview(reviewId: String)
    suspend fun likeEntry(entryId: String)
    suspend fun unlikeEntry(entryId: String)
}
