package com.lasecun.goodwines.features.auth.data.source.local

import com.lasecun.goodwines.features.auth.domain.model.AuthSession

/**
 * Local contract for session persistence.
 * MVP uses an in-memory implementation; a future task will replace it
 * with encrypted platform storage.
 */
interface LocalSessionDataSource {
    fun saveSession(session: AuthSession)
    fun getSession(): AuthSession?
    fun clearSession()
}
