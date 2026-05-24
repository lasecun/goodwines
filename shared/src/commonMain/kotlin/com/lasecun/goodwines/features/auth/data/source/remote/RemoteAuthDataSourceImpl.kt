package com.lasecun.goodwines.features.auth.data.source.remote

import com.lasecun.goodwines.core.data.network.ApiException
import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.auth.data.source.remote.dto.AuthSessionDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.RegisterRequestDto
import com.lasecun.goodwines.features.auth.data.source.remote.dto.SignInRequestDto

/**
 * Stub implementation — replaced by a real Supabase/HTTP client
 * in the authentication MVP task (#9).
 */
class RemoteAuthDataSourceImpl : RemoteAuthDataSource {
    override suspend fun signIn(request: SignInRequestDto): NetworkResult<AuthSessionDto> =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun register(request: RegisterRequestDto): NetworkResult<AuthSessionDto> =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun signOut(accessToken: String): NetworkResult<Unit> =
        NetworkResult.Success(Unit)

    override suspend fun refreshSession(refreshToken: String): NetworkResult<AuthSessionDto> =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))
}
