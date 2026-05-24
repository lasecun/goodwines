package com.lasecun.goodwines.features.user.data.repository

import com.lasecun.goodwines.features.user.domain.model.User
import com.lasecun.goodwines.features.user.domain.repository.UserRepository

// Stub — replace with real local/remote sources in foundation-room-persistence (#4)
class UserRepositoryImpl : UserRepository {
    override suspend fun getUserById(id: String): User? = null
    override suspend fun saveUser(user: User) = Unit
    override suspend fun getCurrentUser(): User? = null
}
