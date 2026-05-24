package com.lasecun.goodwines.features.auth.domain.usecase

import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        repository.signOut()
    }
}
