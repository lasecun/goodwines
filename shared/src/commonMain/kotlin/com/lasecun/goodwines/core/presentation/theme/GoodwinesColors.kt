package com.lasecun.goodwines.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ── Brand palette ──────────────────────────────────────────────────────────
// Primary: deep burgundy / wine red
val WineRed = Color(0xFF8B1A2B)
val WineRedLight = Color(0xFFB5374F)
val WineRedDark = Color(0xFF5C1020)
val OnWineRed = Color(0xFFFFFFFF)
val WineRedContainer = Color(0xFFFFDADE)
val OnWineRedContainer = Color(0xFF3B0011)

// Secondary: warm amber / golden label
val WineGold = Color(0xFF7A5830)
val WineGoldLight = Color(0xFFC4923A)
val WineGoldContainer = Color(0xFFFFDDB3)
val OnWineGold = Color(0xFFFFFFFF)
val OnWineGoldContainer = Color(0xFF2B1700)

// Tertiary: dusty rose / blush
val WineBlush = Color(0xFF785060)
val WineBlushContainer = Color(0xFFFFD8E4)
val OnWineBlush = Color(0xFFFFFFFF)
val OnWineBlushContainer = Color(0xFF31101D)

// Neutrals
val WineSurface = Color(0xFFFFF8F7)       // warm off-white surface
val WineBackground = Color(0xFFFFFBF9)    // warm near-white background

// Dark palette ──────────────────────────────────────────────────────────────
val WineRedDarkTheme = Color(0xFFFFB3BB)  // light rose on dark
val WineRedContainerDark = Color(0xFF6E1020)
val OnWineRedDark = Color(0xFF680020)
val OnWineRedContainerDark = Color(0xFFFFDADE)

val WineGoldDarkTheme = Color(0xFFFFB960)
val WineGoldContainerDark = Color(0xFF5E3E00)
val OnWineGoldDark = Color(0xFF432C00)
val OnWineGoldContainerDark = Color(0xFFFFDDB3)

val WineBlushDarkTheme = Color(0xFFEDB8CC)
val WineBlushContainerDark = Color(0xFF5E3849)
val OnWineBlushDark = Color(0xFF482535)
val OnWineBlushContainerDark = Color(0xFFFFD8E4)

val WineSurfaceDark = Color(0xFF1C1618)   // dark warm surface
val WineBackgroundDark = Color(0xFF110D0E) // near-black warm background

// ── Color schemes ──────────────────────────────────────────────────────────

val GoodwinesLightColorScheme = lightColorScheme(
    primary = WineRed,
    onPrimary = OnWineRed,
    primaryContainer = WineRedContainer,
    onPrimaryContainer = OnWineRedContainer,

    secondary = WineGold,
    onSecondary = OnWineGold,
    secondaryContainer = WineGoldContainer,
    onSecondaryContainer = OnWineGoldContainer,

    tertiary = WineBlush,
    onTertiary = OnWineBlush,
    tertiaryContainer = WineBlushContainer,
    onTertiaryContainer = OnWineBlushContainer,

    background = WineBackground,
    onBackground = Color(0xFF201A1B),

    surface = WineSurface,
    onSurface = Color(0xFF201A1B),
    surfaceVariant = Color(0xFFF4DDDF),
    onSurfaceVariant = Color(0xFF524345),
)

val GoodwinesDarkColorScheme = darkColorScheme(
    primary = WineRedDarkTheme,
    onPrimary = OnWineRedDark,
    primaryContainer = WineRedContainerDark,
    onPrimaryContainer = OnWineRedContainerDark,

    secondary = WineGoldDarkTheme,
    onSecondary = OnWineGoldDark,
    secondaryContainer = WineGoldContainerDark,
    onSecondaryContainer = OnWineGoldContainerDark,

    tertiary = WineBlushDarkTheme,
    onTertiary = OnWineBlushDark,
    tertiaryContainer = WineBlushContainerDark,
    onTertiaryContainer = OnWineBlushContainerDark,

    background = WineBackgroundDark,
    onBackground = Color(0xFFECDEDF),

    surface = WineSurfaceDark,
    onSurface = Color(0xFFECDEDF),
    surfaceVariant = Color(0xFF524345),
    onSurfaceVariant = Color(0xFFD7C1C3),
)
