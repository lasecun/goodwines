package com.lasecun.goodwines.features.social.domain.model

import com.lasecun.goodwines.features.wine.domain.model.Wine

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
