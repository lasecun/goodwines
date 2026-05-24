package com.lasecun.goodwines.features.social.presentation.viewmodel

import com.lasecun.goodwines.features.social.domain.usecase.GetActivityFeedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityFeedViewModel(
    private val getActivityFeedUseCase: GetActivityFeedUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<ActivityFeedUiState>(ActivityFeedUiState.Loading)
    val state: StateFlow<ActivityFeedUiState> = _state.asStateFlow()

    init {
        loadFeed()
    }

    fun loadFeed() {
        scope.launch {
            _state.value = ActivityFeedUiState.Loading
            getActivityFeedUseCase()
                .onSuccess { items ->
                    _state.value = ActivityFeedUiState.Success(items)
                }
                .onFailure { error ->
                    _state.value = ActivityFeedUiState.Error(
                        error.message ?: "Unable to load activity feed."
                    )
                }
        }
    }
}
