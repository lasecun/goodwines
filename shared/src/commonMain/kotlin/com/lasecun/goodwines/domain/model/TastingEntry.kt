// Domain layer — no framework dependencies allowed here.
// Dependency direction: domain ← data, domain ← presentation
package com.lasecun.goodwines.domain.model

data class TastingEntry(
    val id: String,
    val userId: String,
    val wine: Wine,
    val rating: Float,                      // 0.0–5.0
    val notes: String = "",
    val date: Long,                         // epoch millis (kotlinx-datetime added later)
    val location: String? = null,
    val foodPairing: String? = null,
    val mood: String? = null,
    val isPublic: Boolean = false
)
