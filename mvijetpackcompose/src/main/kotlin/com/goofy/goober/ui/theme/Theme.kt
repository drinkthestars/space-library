package com.goofy.goober.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Indigo,
    primaryVariant = MediumPurple,
    onPrimary = Color.White,
    secondary = Indigo,
    secondaryVariant = Color.White,
    onSecondary = BlueViolet,
    background = Color.White
)

private val DarkThemeColors = darkColors(
    primary = Indigo,
    primaryVariant = BlueViolet,
    onPrimary = Color.White,
    secondary = DarkMagenta,
    onSecondary = Color.White,
    background = DarkBlueGrey
)

@Composable
fun AstroAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
