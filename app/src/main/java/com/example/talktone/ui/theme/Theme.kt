package com.example.talktone.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val EthiopianGold = Color(0xFFD4AF37)
val EthiopianGreen = Color(0xFF078930)
val EthiopianRed = Color(0xFFDA121A)
val DeepPurple = Color(0xFF1A0A2E)
val MidnightBlue = Color(0xFF16213E)
val RoyalPurple = Color(0xFF4A0E8F)
val CreamWhite = Color(0xFFFFF8E7)
val WarmGold = Color(0xFFFFD700)

private val DarkColorScheme = darkColorScheme(
    primary = EthiopianGold,
    onPrimary = DeepPurple,
    primaryContainer = RoyalPurple,
    onPrimaryContainer = CreamWhite,
    secondary = EthiopianGreen,
    onSecondary = Color.White,
    background = DeepPurple,
    onBackground = CreamWhite,
    surface = MidnightBlue,
    onSurface = CreamWhite,
    surfaceVariant = Color(0xFF2A1A4E),
    onSurfaceVariant = Color(0xFFCCBB99),
    error = EthiopianRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5C1A8A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDD9FF),
    onPrimaryContainer = Color(0xFF2A0050),
    secondary = EthiopianGreen,
    onSecondary = Color.White,
    background = Color(0xFFFFF8F0),
    onBackground = Color(0xFF1A0A2E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A0A2E),
    surfaceVariant = Color(0xFFF3E5FF),
    onSurfaceVariant = Color(0xFF4A3060),
    error = EthiopianRed,
    onError = Color.White
)

@Composable
fun TalktoneTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
