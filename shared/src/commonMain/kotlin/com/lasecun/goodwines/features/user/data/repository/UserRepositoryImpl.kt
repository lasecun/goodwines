package com.lasecun.goodwines.features.user.data.repository

import com.lasecun.goodwines.core.data.network.getOrNull
import com.lasecun.goodwines.features.user.data.source.remote.RemoteUserDataSource
import com.lasecun.goodwines.features.user.data.source.remote.dto.UserDto
import com.lasecun.goodwines.features.user.domain.model.TasteProfile
import com.lasecun.goodwines.features.user.domain.model.User
import com.lasecun.goodwines.features.user.domain.repository.UserRepository

class UserRepositoryImpl(
    private val remoteDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun getUserById(id: String): User? =
        remoteDataSource.getUserById(id).getOrNull()?.toDomain()

    override suspend fun saveUser(user: User) = Unit // persisted via auth session in MVP task

    override suspend fun getCurrentUser(): User? =
        remoteDataSource.getCurrentUser().getOrNull()?.toDomain()

    private fun UserDto.toDomain() = User(
        id = id,
        username = username,
        displayName = displayName,
        bio = bio,
        avatarUrl = avatarUrl,
        followersCount = followersCount,
        followingCount = followingCount,
        tasteProfile = TasteProfile(
            preferredStyles = tasteProfile.preferredStyles,
            preferredRegions = tasteProfile.preferredRegions,
            preferredGrapes = tasteProfile.preferredGrapes
        )
    )
}
