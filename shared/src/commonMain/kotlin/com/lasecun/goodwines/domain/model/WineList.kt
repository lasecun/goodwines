// Domain layer — no framework dependencies allowed here.
// Dependency direction: domain ← data, domain ← presentation
package com.lasecun.goodwines.domain.model

data class WineList(
    val id: String,
    val ownerId: String,
    val name: String,
    val type: WineListType = WineListType.CUSTOM,
    val wines: List<Wine> = emptyList()
)

enum class WineListType {
    FAVORITES, WISHLIST, TRIED, CUSTOM
}
