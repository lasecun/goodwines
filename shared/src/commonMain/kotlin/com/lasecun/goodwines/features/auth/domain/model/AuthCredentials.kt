package com.lasecun.goodwines.features.auth.domain.model

/** Credentials supplied by the user at sign-in. */
data class AuthCredentials(
    val email: String,
    val password: String
)

/** Credentials for new account registration. */
data class RegisterCredentials(
    val email: String,
    val password: String,
    val username: String,
    val displayName: String
)
