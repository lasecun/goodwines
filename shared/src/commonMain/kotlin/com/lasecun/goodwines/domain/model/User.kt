// Domain layer — no framework dependencies allowed here.
// Dependency direction: domain ← data, domain ← presentation
package com.lasecun.goodwines.domain.model

data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val bio: String = "",
    val avatarUrl: String? = null,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val tasteProfile: TasteProfile = TasteProfile()
)

data class TasteProfile(
    val preferredStyles: List<String> = emptyList(),
    val preferredRegions: List<String> = emptyList(),
    val preferredGrapes: List<String> = emptyList()
)
