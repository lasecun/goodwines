package com.lasecun.goodwines.features.auth.presentation.viewmodel

import com.lasecun.goodwines.features.auth.domain.usecase.GetCurrentSessionUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.RegisterUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.SignInUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.SignOutUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Presentation state holder for the authentication flow.
 * Scoped to the app process — auth state is global.
 */
class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    private val _loginForm = MutableStateFlow<LoginFormState>(LoginFormState.Idle)
    val loginForm: StateFlow<LoginFormState> = _loginForm.asStateFlow()

    private val _registerForm = MutableStateFlow<RegisterFormState>(RegisterFormState.Idle)
    val registerForm: StateFlow<RegisterFormState> = _registerForm.asStateFlow()

    init {
        checkSession()
    }

    fun checkSession() {
        scope.launch {
            _authState.value = AuthUiState.Loading
            val session = getCurrentSessionUseCase()
            _authState.value = if (session != null) {
                AuthUiState.LoggedIn(session)
            } else {
                AuthUiState.LoggedOut
            }
        }
    }

    fun signIn(email: String, password: String) {
        scope.launch {
            _loginForm.value = LoginFormState.Loading
            signInUseCase(email, password)
                .onSuccess { session ->
                    _loginForm.value = LoginFormState.Idle
                    _authState.value = AuthUiState.LoggedIn(session)
                }
                .onFailure { error ->
                    _loginForm.value = LoginFormState.Error(
                        error.message ?: "Sign in failed. Please try again."
                    )
                }
        }
    }

    fun register(email: String, password: String, username: String, displayName: String) {
        scope.launch {
            _registerForm.value = RegisterFormState.Loading
            registerUseCase(email, password, username, displayName)
                .onSuccess { session ->
                    _registerForm.value = RegisterFormState.Idle
                    _authState.value = AuthUiState.LoggedIn(session)
                }
                .onFailure { error ->
                    _registerForm.value = RegisterFormState.Error(
                        error.message ?: "Registration failed. Please try again."
                    )
                }
        }
    }

    fun signOut() {
        scope.launch {
            signOutUseCase()
            _authState.value = AuthUiState.LoggedOut
        }
    }

    fun clearLoginError() {
        _loginForm.value = LoginFormState.Idle
    }

    fun clearRegisterError() {
        _registerForm.value = RegisterFormState.Idle
    }
}
