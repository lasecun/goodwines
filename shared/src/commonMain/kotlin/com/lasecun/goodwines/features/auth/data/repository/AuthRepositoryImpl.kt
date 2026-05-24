package com.lasecun.goodwines.features.auth.data.repository

import com.lasecun.goodwines.core.data.network.getOrNull
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.dto.AuthSessionDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.RegisterRequestDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.SignInRequestDto
import com.lasecun.goodwines.features.auth.domain.model.AuthCredentials
import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.model.RegisterCredentials
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: RemoteAuthDataSource
) : AuthRepository {

    override suspend fun signIn(credentials: AuthCredentials): AuthSession {
        val result = remoteDataSource.signIn(
            SignInRequestDto(email = credentials.email, password = credentials.password)
        )
        return result.getOrNull()?.toDomain()
            ?: error("Sign in failed")
    }

    override suspend fun register(credentials: RegisterCredentials): AuthSession {
        val result = remoteDataSource.register(
            RegisterRequestDto(
                email = credentials.email,
                password = credentials.password,
                username = credentials.username,
                displayName = credentials.displayName
            )
        )
        return result.getOrNull()?.toDomain()
            ?: error("Registration failed")
    }

    override suspend fun signOut() {
        // Token management added in MVP auth task
    }

    override suspend fun getCurrentSession(): AuthSession? = null // persisted in MVP auth task

    override suspend fun refreshSession(): AuthSession? {
        return null // implemented in MVP auth task
    }

    private fun AuthSessionDto.toDomain() = AuthSession(
        userId = userId,
        email = email,
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresAt = expiresAt
    )
}
