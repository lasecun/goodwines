package com.lasecun.goodwines.features.wine.domain.model

data class Wine(
    val id: String,
    val name: String,
    val winery: String,
    val vintage: Int? = null,
    val region: String = "",
    val country: String = "",
    val grapes: List<String> = emptyList(),
    val style: WineStyle = WineStyle.RED,
    val imageUrl: String? = null,
    val averageRating: Float? = null,
    val abv: Float? = null,
    val description: String = ""
)

enum class WineStyle {
    RED, WHITE, ROSÉ, SPARKLING, DESSERT, FORTIFIED
}
