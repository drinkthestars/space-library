package com.goofy.goober.ui

import com.goofy.goober.model.AstroIntent
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.DisplayingDetailsState
import com.goofy.goober.model.ImageSearchState
import com.goofy.goober.model.SplashState
import com.goofy.goober.ui.navigation.NavRouter

internal class AstroRenderer(
    private val navRouter: NavRouter
) {

    fun render(
        astroState: AstroState,
        onIntent: (AstroIntent) -> Unit
    ) {
        astroState.renderInternal(onIntent)
    }

    private fun AstroState.renderInternal(onIntent: (AstroIntent) -> Unit) {
        when (this) {
            SplashState -> {
            }
            ImageSearchState -> {
            }
            DisplayingDetailsState -> {
            }
        }.let {}
    }
}
