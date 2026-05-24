// Domain layer — repository contracts only. No implementation details here.
package com.lasecun.goodwines.domain.repository

import com.lasecun.goodwines.domain.model.User

interface UserRepository {
    suspend fun getUserById(id: String): User?
    suspend fun saveUser(user: User)
    suspend fun getCurrentUser(): User?
}
