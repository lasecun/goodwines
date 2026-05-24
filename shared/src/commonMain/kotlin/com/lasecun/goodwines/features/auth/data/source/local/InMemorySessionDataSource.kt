package com.lasecun.goodwines.features.auth.data.source.local

import com.lasecun.goodwines.features.auth.domain.model.AuthSession

/**
 * In-memory session store for MVP.
 * TODO: Replace with encrypted platform storage (Keychain on iOS, EncryptedSharedPreferences on Android).
 */
class InMemorySessionDataSource : LocalSessionDataSource {
    private var session: AuthSession? = null

    override fun saveSession(session: AuthSession) {
        this.session = session
    }

    override fun getSession(): AuthSession? = session

    override fun clearSession() {
        session = null
    }
}
