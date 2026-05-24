package com.lasecun.goodwines.features.auth.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String,
    @SerialName("display_name") val displayName: String
)

@Serializable
data class AuthSessionDto(
    @SerialName("user_id") val userId: String,
    val email: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("expires_at") val expiresAt: Long? = null
)
