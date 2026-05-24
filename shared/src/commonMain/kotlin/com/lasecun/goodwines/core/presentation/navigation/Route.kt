package com.lasecun.goodwines.core.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Typed route definitions for the app navigation graph.
 * Serializable enables type-safe argument passing via Navigation Compose.
 */
sealed interface Route {

    @Serializable
    data object WineList : Route

    @Serializable
    data class WineDetail(val wineId: String) : Route

    @Serializable
    data object Journal : Route

    @Serializable
    data class JournalEntry(val entryId: String) : Route

    @Serializable
    data object UserProfile : Route

    @Serializable
    data object ActivityFeed : Route
}
