package com.example.klexifrontend.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object KlexiColors {
    val PurplePrimary = Color(0xFF6C5CE7)
    val PurpleDark = Color(0xFF4834D4)
    val PurpleLight = Color(0xFFE8E5FF)
    val PurpleButton = Color(0xFF5B4BD8)

    val SkyBlue = Color(0xFF89CFF0)
    val SkyLight = Color(0xFFE0F2FE)
    val CloudWhite = Color(0xFFFFFFFF)

    val GrassGreen = Color(0xFF6CB83E)
    val GrassDark = Color(0xFF4A8F24)
    val BrickBrown = Color(0xFFBD6E39)
    val BrickDark = Color(0xFF964B00)

    val CardBackground = Color(0xFFFFF9EE)
    val CardBorder = Color(0xFFE2D6C5)

    val StarYellow = Color(0xFFFFD166)
    val StarDark = Color(0xFFF59E0B)
    val AlertPink = Color(0xFFFF6B81)
    val SuccessGreen = Color(0xFF2ECC71)

    val TextDark = Color(0xFF2D1B69)
    val TextMuted = Color(0xFF5A4D80)
    val TextLight = Color(0xFFFFFFFF)
}

val KlexiShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

private val LightColorScheme = lightColorScheme(
    primary = KlexiColors.PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = KlexiColors.PurpleLight,
    onPrimaryContainer = KlexiColors.TextDark,
    secondary = KlexiColors.StarYellow,
    onSecondary = KlexiColors.TextDark,
    background = KlexiColors.SkyLight,
    surface = KlexiColors.CardBackground,
    onSurface = KlexiColors.TextDark
)

@Composable
fun KlexiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes = KlexiShapes,
        content = content
    )
}
