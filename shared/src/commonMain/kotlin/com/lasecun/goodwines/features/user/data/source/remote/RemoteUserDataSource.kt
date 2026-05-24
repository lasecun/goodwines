package com.lasecun.goodwines.features.user.data.source.remote

import com.lasecun.goodwines.core.data.network.NetworkResult
import com.lasecun.goodwines.features.user.data.source.remote.dto.UpdateUserRequestDto
import com.lasecun.goodwines.features.user.data.source.remote.dto.UserDto

/**
 * Remote contract for user profile operations.
 */
interface RemoteUserDataSource {
    suspend fun getUserById(id: String): NetworkResult<UserDto>
    suspend fun getCurrentUser(): NetworkResult<UserDto>
    suspend fun updateUser(id: String, request: UpdateUserRequestDto): NetworkResult<UserDto>
    suspend fun followUser(targetUserId: String): NetworkResult<Unit>
    suspend fun unfollowUser(targetUserId: String): NetworkResult<Unit>
    suspend fun getFollowers(userId: String, page: Int = 0): NetworkResult<List<UserDto>>
    suspend fun getFollowing(userId: String, page: Int = 0): NetworkResult<List<UserDto>>
}
