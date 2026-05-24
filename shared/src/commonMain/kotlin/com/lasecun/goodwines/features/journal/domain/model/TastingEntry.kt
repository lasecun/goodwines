package com.lasecun.goodwines.features.journal.domain.model

import com.lasecun.goodwines.features.wine.domain.model.Wine

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
