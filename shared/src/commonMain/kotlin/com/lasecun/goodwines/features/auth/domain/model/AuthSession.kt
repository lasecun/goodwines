package com.lasecun.goodwines.features.auth.domain.model

/**
 * Represents an authenticated user session.
 * The token is opaque to the domain — only the data layer deals with
 * bearer headers and refresh logic.
 */
data class AuthSession(
    val userId: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String?,
    val expiresAt: Long?   // epoch millis; null = no expiry info
)
