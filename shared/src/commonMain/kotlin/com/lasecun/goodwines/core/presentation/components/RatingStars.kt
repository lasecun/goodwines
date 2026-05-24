package com.lasecun.goodwines.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

/**
 * 5-star rating row used in tasting entries and wine cards.
 *
 * Uses unicode star glyphs rendered as Text to avoid extending the
 * material-icons dependency beyond the core set.
 *
 * @param rating   Current rating value (0.0 – 5.0). Whole stars only for input.
 * @param onRate   If non-null, stars become interactive. Pass null for display-only.
 * @param starSize Size of each star icon.
 */
@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    onRate: ((Int) -> Unit)? = null,
    starSize: Dp = 24.dp,
    maxStars: Int = 5
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 1..maxStars) {
            val filled = index <= rating
            val tint = if (filled) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
            val starChar = if (filled) "★" else "☆"

            if (onRate != null) {
                IconButton(
                    onClick = { onRate(index) },
                ) {
                    Text(
                        text = starChar,
                        color = tint,
                        fontSize = TextUnit(starSize.value, TextUnitType.Sp)
                    )
                }
            } else {
                Text(
                    text = starChar,
                    color = tint,
                    fontSize = TextUnit(starSize.value, TextUnitType.Sp)
                )
            }
        }
    }
}
