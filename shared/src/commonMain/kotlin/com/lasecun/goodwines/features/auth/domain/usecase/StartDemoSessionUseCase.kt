package com.lasecun.goodwines.features.auth.domain.usecase

import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class StartDemoSessionUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<AuthSession> =
        runCatching {
            repository.startDemoSession(email)
        }
}
