package com.lasecun.goodwines.features.auth.data.repository

import com.lasecun.goodwines.core.data.network.getOrNull
import com.lasecun.goodwines.features.auth.data.source.local.LocalSessionDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.dto.AuthSessionDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.RegisterRequestDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.SignInRequestDto
import com.lasecun.goodwines.features.auth.domain.model.AuthCredentials
import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.model.RegisterCredentials
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository
import com.lasecun.goodwines.core.platform.randomUuid

class AuthRepositoryImpl(
    private val remoteDataSource: RemoteAuthDataSource,
    private val localSession: LocalSessionDataSource
) : AuthRepository {

    override suspend fun signIn(credentials: AuthCredentials): AuthSession {
        val result = remoteDataSource.signIn(
            SignInRequestDto(email = credentials.email, password = credentials.password)
        )
        val session = result.getOrNull()?.toDomain() ?: error("Sign in failed")
        localSession.saveSession(session)
        return session
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
        val session = result.getOrNull()?.toDomain() ?: error("Registration failed")
        localSession.saveSession(session)
        return session
    }

    override suspend fun signOut() {
        val token = localSession.getSession()?.accessToken ?: return
        remoteDataSource.signOut(token)
        localSession.clearSession()
    }

    override suspend fun getCurrentSession(): AuthSession? = localSession.getSession()

    override suspend fun refreshSession(): AuthSession? {
        val refreshToken = localSession.getSession()?.refreshToken ?: return null
        val result = remoteDataSource.refreshSession(refreshToken)
        val session = result.getOrNull()?.toDomain() ?: return null
        localSession.saveSession(session)
        return session
    }

    override suspend fun startDemoSession(email: String): AuthSession {
        val normalizedEmail = email.trim().ifBlank { "demo@goodwines.app" }
        val demoSession = AuthSession(
            userId = "demo-${randomUuid()}",
            email = normalizedEmail,
            accessToken = "demo-access-token",
            refreshToken = null,
            expiresAt = null,
            isDemo = true
        )
        localSession.saveSession(demoSession)
        return demoSession
    }

    private fun AuthSessionDto.toDomain() = AuthSession(
        userId = userId,
        email = email,
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresAt = expiresAt
    )
}
