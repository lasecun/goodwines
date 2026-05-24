package com.lasecun.goodwines.features.auth.data.source.remote

import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.auth.data.source.remote.dto.AuthSessionDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.RegisterRequestDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.SignInRequestDto

/**
 * Remote contract for authentication.
 * Implemented by a Supabase/custom-backend client in the infra layer.
 */
interface RemoteAuthDataSource {
    suspend fun signIn(request: SignInRequestDto): NetworkResult<AuthSessionDto>
    suspend fun register(request: RegisterRequestDto): NetworkResult<AuthSessionDto>
    suspend fun signOut(accessToken: String): NetworkResult<Unit>
    suspend fun refreshSession(refreshToken: String): NetworkResult<AuthSessionDto>
}
