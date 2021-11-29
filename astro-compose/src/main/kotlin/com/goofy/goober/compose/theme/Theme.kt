package com.goofy.goober.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val AstroColors = darkColors(
    primary = Indigo,
    primaryVariant = BlueViolet,
    onPrimary = SplashBg,
    secondary = DarkBlueGrey,
//    onSecondary = Spinner,
    background = DarkBlueGrey
)

@Composable
fun AstroAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = AstroColors,
        content = content
    )
}
