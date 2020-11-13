package com.goofy.goober.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlueViolet = Color(0x000024)
private val DarkBlueGrey = Color(0xff1e1e2f)
private val Indigo = Color(0x1c2a4c)

private val ThemeColors = darkColors(
    primary = Indigo,
    primaryVariant = BlueViolet,
    onPrimary = SplashBg,
    secondary = DarkBlueGrey,
    onSecondary = SplashBg,
    background = DarkBlueGrey
)

@Composable
fun AstroAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = ThemeColors,
        content = content
    )
}
