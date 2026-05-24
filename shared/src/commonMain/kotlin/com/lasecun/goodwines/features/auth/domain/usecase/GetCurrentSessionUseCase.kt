package com.lasecun.goodwines.features.auth.domain.usecase

import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class GetCurrentSessionUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): AuthSession? = repository.getCurrentSession()
}
