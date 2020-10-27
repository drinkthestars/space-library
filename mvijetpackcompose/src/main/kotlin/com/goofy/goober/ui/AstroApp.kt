package com.goofy.goober.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.goofy.goober.model.AstroIntent
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.DisplayingDetailsState
import com.goofy.goober.model.ImageSearchState
import com.goofy.goober.model.SplashState

@Composable
internal fun AstroApp(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            SplashState -> Surface(color = MaterialTheme.colors.onPrimary) { }
            ImageSearchState -> Surface(color = MaterialTheme.colors.onSecondary) { }
            DisplayingDetailsState -> Surface(color = MaterialTheme.colors.primaryVariant) { }
        }
    }
}
