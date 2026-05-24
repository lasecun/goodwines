// Data layer — remote data source contract.
// Implemented when backend/Supabase integration is added.
package com.lasecun.goodwines.data.source.remote

import com.lasecun.goodwines.domain.model.User
import com.lasecun.goodwines.domain.model.Wine

interface RemoteWineDataSource {
    suspend fun searchWines(query: String): List<Wine>
    suspend fun getWineById(id: String): Wine?
}

interface RemoteUserDataSource {
    suspend fun getUserById(id: String): User?
}
