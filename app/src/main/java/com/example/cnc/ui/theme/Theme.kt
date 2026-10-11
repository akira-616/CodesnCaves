package com.example.cnc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlueEclipse = darkColorScheme(
    primary = Lavender, onPrimary = Midnight,
    secondary = Indigo, onSecondary = Color.White,
    primaryContainer = Indigo, onPrimaryContainer = Color.White,
    background = Midnight, onBackground = TextLight,
    surface = Midnight, onSurface = TextLight,
    surfaceVariant = DeepNavy, onSurfaceVariant = Color(0xFFCFCFE6),
    surfaceContainer = DeepNavy, surfaceContainerHigh = DeepNavy,
    surfaceContainerHighest = DeepNavy, surfaceContainerLow = DeepNavy,
    outline = Lavender
)

@Composable
fun CncTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = BlueEclipse, typography = Typography, content = content)
}
