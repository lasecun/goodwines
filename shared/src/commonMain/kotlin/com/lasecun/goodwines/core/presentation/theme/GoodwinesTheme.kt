package com.lasecun.goodwines.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Goodwines design system theme.
 *
 * Applies the wine-inspired color scheme (burgundy / gold / blush),
 * editorial typography, and slightly rounded shapes. Supports both
 * light and dark mode, with the dark scheme offering a premium
 * near-black warm palette.
 */
@Composable
fun GoodwinesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) GoodwinesDarkColorScheme else GoodwinesLightColorScheme,
        typography = GoodwinesTypography,
        shapes = GoodwinesShapes,
        content = content
    )
}
