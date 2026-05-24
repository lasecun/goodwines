package com.lasecun.goodwines.features.user.presentation.viewmodel

import com.lasecun.goodwines.features.user.domain.model.User

sealed interface UserProfileUiState {
    data object Loading : UserProfileUiState
    data class Error(val message: String) : UserProfileUiState
    data class Success(
        val user: User,
        val stats: UserProfileStats
    ) : UserProfileUiState
}

data class UserProfileStats(
    val followersCount: Int,
    val followingCount: Int,
    val tastingEntriesCount: Int = 0,
    val favoriteWinesCount: Int = 0,
    val reviewsCount: Int = 0
)
