// Domain layer — no framework dependencies allowed here.
// Dependency direction: domain ← data, domain ← presentation
package com.lasecun.goodwines.domain.model

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
    val averageRating: Float? = null
)

enum class WineStyle {
    RED, WHITE, ROSÉ, SPARKLING, DESSERT, FORTIFIED
}
