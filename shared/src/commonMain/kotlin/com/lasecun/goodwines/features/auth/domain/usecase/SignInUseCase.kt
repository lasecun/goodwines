package com.lasecun.goodwines.features.auth.domain.usecase

import com.lasecun.goodwines.features.auth.domain.model.AuthCredentials
import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<AuthSession> =
        runCatching {
            require(email.isNotBlank()) { "Email cannot be empty" }
            require(password.isNotBlank()) { "Password cannot be empty" }
            repository.signIn(AuthCredentials(email.trim(), password))
        }
}
