package com.lasecun.goodwines.features.user.data.source.remote

import com.lasecun.goodwines.core.data.network.ApiException
import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.user.data.source.remote.dto.UpdateUserRequestDto
import com.lasecun.goodwines.features.user.data.source.remote.dto.UserDto

/** Stub — replaced by real HTTP client in infra-backend-integration task. */
class RemoteUserDataSourceImpl : RemoteUserDataSource {
    private val notConfigured get() =
        NetworkResult.Failure(ApiException.NetworkUnavailable("Backend not yet configured"))

    override suspend fun getUserById(id: String): NetworkResult<UserDto> = notConfigured
    override suspend fun getCurrentUser(): NetworkResult<UserDto> = notConfigured
    override suspend fun updateUser(id: String, request: UpdateUserRequestDto): NetworkResult<UserDto> = notConfigured
    override suspend fun followUser(targetUserId: String): NetworkResult<Unit> = notConfigured
    override suspend fun unfollowUser(targetUserId: String): NetworkResult<Unit> = notConfigured
    override suspend fun getFollowers(userId: String, page: Int): NetworkResult<List<UserDto>> = notConfigured
    override suspend fun getFollowing(userId: String, page: Int): NetworkResult<List<UserDto>> = notConfigured
}
