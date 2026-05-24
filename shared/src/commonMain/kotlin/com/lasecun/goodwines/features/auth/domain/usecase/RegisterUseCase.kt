package com.lasecun.goodwines.features.auth.domain.usecase

import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.model.RegisterCredentials
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        username: String,
        displayName: String
    ): Result<AuthSession> = runCatching {
        require(email.isNotBlank()) { "Email cannot be empty" }
        require(password.length >= 8) { "Password must be at least 8 characters" }
        require(username.isNotBlank()) { "Username cannot be empty" }
        require(displayName.isNotBlank()) { "Display name cannot be empty" }
        repository.register(
            RegisterCredentials(
                email = email.trim(),
                password = password,
                username = username.trim(),
                displayName = displayName.trim()
            )
        )
    }
}
