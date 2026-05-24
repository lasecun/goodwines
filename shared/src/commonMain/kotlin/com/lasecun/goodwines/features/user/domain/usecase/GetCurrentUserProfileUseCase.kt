package com.lasecun.goodwines.features.user.domain.usecase

import com.lasecun.goodwines.features.auth.domain.usecase.GetCurrentSessionUseCase
import com.lasecun.goodwines.features.user.domain.model.User
import com.lasecun.goodwines.features.user.domain.repository.UserRepository

/**
 * Loads the current user's profile.
 *
 * MVP behavior:
 * - Prefer the remote profile when available.
 * - Fall back to a lightweight profile derived from the current auth session.
 */
class GetCurrentUserProfileUseCase(
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        val session = getCurrentSessionUseCase() ?: return null

        return userRepository.getCurrentUser() ?: User(
            id = session.userId,
            username = session.email.substringBefore("@"),
            displayName = session.email.substringBefore("@"),
            bio = "Wine lover 🍷"
        )
    }
}
