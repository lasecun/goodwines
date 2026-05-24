package com.lasecun.goodwines.features.user.presentation.viewmodel

import com.lasecun.goodwines.features.user.domain.usecase.GetCurrentUserProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val getCurrentUserProfileUseCase: GetCurrentUserProfileUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val state: StateFlow<UserProfileUiState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        scope.launch {
            _state.value = UserProfileUiState.Loading
            val user = getCurrentUserProfileUseCase()
            _state.value = if (user != null) {
                UserProfileUiState.Success(
                    user = user,
                    stats = UserProfileStats(
                        followersCount = user.followersCount,
                        followingCount = user.followingCount
                    )
                )
            } else {
                UserProfileUiState.Error("You need to sign in to view your profile.")
            }
        }
    }
}
