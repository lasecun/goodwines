package com.lasecun.goodwines.features.user.domain.repository

import com.lasecun.goodwines.features.user.domain.model.User

interface UserRepository {
    suspend fun getUserById(id: String): User?
    suspend fun saveUser(user: User)
    suspend fun getCurrentUser(): User?
}
