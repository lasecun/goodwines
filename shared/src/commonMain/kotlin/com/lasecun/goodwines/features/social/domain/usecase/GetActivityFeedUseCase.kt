package com.lasecun.goodwines.features.social.domain.usecase

import com.lasecun.goodwines.features.auth.domain.usecase.GetCurrentSessionUseCase
import com.lasecun.goodwines.features.social.domain.model.ActivityFeedItem
import com.lasecun.goodwines.features.social.domain.model.ActivityType
import com.lasecun.goodwines.features.social.domain.model.FeedInteractions
import com.lasecun.goodwines.features.social.domain.repository.SocialRepository

class GetActivityFeedUseCase(
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val socialRepository: SocialRepository
) {
    suspend operator fun invoke(): Result<List<ActivityFeedItem>> = runCatching {
        val session = getCurrentSessionUseCase()
            ?: throw IllegalStateException("You need to sign in to load activity feed.")

        val remoteFeed = socialRepository.getActivityFeed(userId = session.userId)
        if (remoteFeed.isNotEmpty()) {
            remoteFeed
        } else {
            demoFeed(session.userId)
        }
    }

    private fun demoFeed(currentUserId: String): List<ActivityFeedItem> {
        val now = 1_700_000_000_000L
        return listOf(
            ActivityFeedItem(
                id = "demo-1",
                type = ActivityType.TASTING_ENTRY,
                actorUserId = "friend-1",
                actorUsername = "sofia_wines",
                wineName = "Rioja Reserva",
                wineWinery = "Marqués de Riscal",
                rating = 4.5f,
                text = "Cherry, vanilla and a smooth finish. Great with roast lamb.",
                createdAt = now,
                interactions = FeedInteractions(likesCount = 12, commentsCount = 3)
            ),
            ActivityFeedItem(
                id = "demo-2",
                type = ActivityType.REVIEW,
                actorUserId = "friend-2",
                actorUsername = "vinocarlos",
                wineName = "Albariño",
                wineWinery = "Pazo de Señorans",
                rating = 4.0f,
                text = "Fresh and mineral. Perfect summer white.",
                createdAt = now - 3_600_000L,
                interactions = FeedInteractions(likesCount = 7, commentsCount = 1)
            ),
            ActivityFeedItem(
                id = "demo-3",
                type = ActivityType.FOLLOW,
                actorUserId = currentUserId,
                actorUsername = "you",
                text = "Your network is growing. Follow more tasters to personalize this feed.",
                createdAt = now - 7_200_000L
            )
        )
    }
}
