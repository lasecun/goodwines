// Data layer — stub implementations. Replace with real Room/remote sources in later tasks.
// data → domain (inward dependency only)
package com.lasecun.goodwines.data.repository

import com.lasecun.goodwines.domain.model.User
import com.lasecun.goodwines.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun getUserById(id: String): User? = null
    override suspend fun saveUser(user: User) = Unit
    override suspend fun getCurrentUser(): User? = null
}
