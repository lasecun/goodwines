package com.lasecun.goodwines.features.user.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val username: String,
    @SerialName("display_name") val displayName: String,
    val bio: String = "",
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("followers_count") val followersCount: Int = 0,
    @SerialName("following_count") val followingCount: Int = 0,
    @SerialName("taste_profile") val tasteProfile: TasteProfileDto = TasteProfileDto()
)

@Serializable
data class TasteProfileDto(
    @SerialName("preferred_styles") val preferredStyles: List<String> = emptyList(),
    @SerialName("preferred_regions") val preferredRegions: List<String> = emptyList(),
    @SerialName("preferred_grapes") val preferredGrapes: List<String> = emptyList()
)

@Serializable
data class UpdateUserRequestDto(
    @SerialName("display_name") val displayName: String? = null,
    val bio: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
)
