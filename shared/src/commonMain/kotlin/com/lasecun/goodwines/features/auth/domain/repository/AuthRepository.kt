package com.lasecun.goodwines.features.auth.domain.repository

import com.lasecun.goodwines.features.auth.domain.model.AuthCredentials
import com.lasecun.goodwines.features.auth.domain.model.AuthSession
import com.lasecun.goodwines.features.auth.domain.model.RegisterCredentials

/**
 * Domain contract for authentication operations.
 * Keeps session management platform-neutral — no token storage details leak here.
 */
interface AuthRepository {
    /** Sign in with email + password. Returns the active session on success. */
    suspend fun signIn(credentials: AuthCredentials): AuthSession

    /** Register a new account. Returns the active session on success. */
    suspend fun register(credentials: RegisterCredentials): AuthSession

    /** Sign out and invalidate the current session. */
    suspend fun signOut()

    /** Returns the current session if one exists and is valid, null otherwise. */
    suspend fun getCurrentSession(): AuthSession?

    /** Attempt to refresh the access token using the refresh token. */
    suspend fun refreshSession(): AuthSession?
}
