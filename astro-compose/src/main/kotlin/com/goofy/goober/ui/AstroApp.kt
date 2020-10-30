package com.goofy.goober.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.goofy.goober.common.R
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.DisplayingDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.ui.theme.SplashBg
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

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

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Splash(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    Surface(color = SplashBg, modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = true,
            initiallyVisible = false,
            enter = fadeIn(initialAlpha = 0f)
        ) {
            Image(
                asset = vectorResource(R.drawable.ic_nasa_vector_logo),
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.None
            )
        }
    }
}

@Composable
internal fun ImageSearch(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    val viewModel = viewModel<ImageSearchViewModel>()
    val state by viewModel.state.collectAsState()
    Surface(color = MaterialTheme.colors.background) {
        Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
        Box(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
            TextField(
                value = "nebula",
                onValueChange = { viewModel.consumeIntent(ImageResultsIntent.Search(it)) }
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
        ScrollableColumn(modifier = Modifier.padding(8.dp)) {
            state.images.forEach { item ->
                Card(modifier = Modifier.fillMaxWidth().preferredHeight(275.dp)) {
                    CoilImage(data = item.thumbUrl, fadeIn = true)
                }
                Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(8.dp))
            }
        }
    }
}

@Composable
internal fun DisplayingDetails(state: AstroState, onIntent: (AstroIntent) -> Unit) {
    val viewModel = viewModel<DetailsViewModel>()
    Surface(color = MaterialTheme.colors.background) {

    }
}
