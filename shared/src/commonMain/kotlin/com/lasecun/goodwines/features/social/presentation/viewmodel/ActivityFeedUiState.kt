package com.lasecun.goodwines.features.social.presentation.viewmodel

import com.lasecun.goodwines.features.social.domain.model.ActivityFeedItem

sealed interface ActivityFeedUiState {
    data object Loading : ActivityFeedUiState
    data class Error(val message: String) : ActivityFeedUiState
    data class Success(val items: List<ActivityFeedItem>) : ActivityFeedUiState
}
