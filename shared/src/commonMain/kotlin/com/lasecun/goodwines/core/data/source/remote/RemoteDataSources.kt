// Core data layer — remote data source contracts.
// Implemented when backend/Supabase integration is added.
package com.lasecun.goodwines.core.data.source.remote

import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.user.domain.model.User

interface RemoteWineDataSource {
    suspend fun searchWines(query: String): List<Wine>
    suspend fun getWineById(id: String): Wine?
}

interface RemoteUserDataSource {
    suspend fun getUserById(id: String): User?
}
