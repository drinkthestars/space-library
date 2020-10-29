package com.goofy.goober.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.DisplayingDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel

@Composable
internal fun AstroApp(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            Splash -> Splash(state, onIntent)
            ImageSearch -> ImageSearch(state, onIntent)
            is DisplayingDetails -> DisplayingDetails(state, onIntent)
        }
    }
}

@Composable
internal fun Splash(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    // TODO : animate and then call onIntent
    Surface(color = MaterialTheme.colors.background) {

    }
}

@Composable
internal fun ImageSearch(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    val viewModel = viewModel<ImageSearchViewModel>()
    Surface(color = MaterialTheme.colors.background) {

    }
}

@Composable
internal fun DisplayingDetails(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    val viewModel = viewModel<DetailsViewModel>()
    Surface(color = MaterialTheme.colors.background) {

    }
}
