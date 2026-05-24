package com.lasecun.goodwines.features.auth.presentation.viewmodel

import com.lasecun.goodwines.features.auth.domain.model.AuthSession

/** All possible states for the authentication flow. */
sealed interface AuthUiState {
    /** Checking for an existing session on startup. */
    data object Loading : AuthUiState

    /** No active session — show login screen. */
    data object LoggedOut : AuthUiState

    /** A session exists — navigate to the main app. */
    data class LoggedIn(val session: AuthSession) : AuthUiState

    /** A transient error message to display. */
    data class Error(val message: String) : AuthUiState
}

/** All possible states for the login form. */
sealed interface LoginFormState {
    data object Idle : LoginFormState
    data object Loading : LoginFormState
    data class Error(val message: String) : LoginFormState
}

/** All possible states for the register form. */
sealed interface RegisterFormState {
    data object Idle : RegisterFormState
    data object Loading : RegisterFormState
    data class Error(val message: String) : RegisterFormState
}
