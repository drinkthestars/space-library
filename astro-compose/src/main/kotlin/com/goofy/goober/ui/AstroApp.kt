package com.goofy.goober.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.goofy.goober.ImageResultItem
import com.goofy.goober.SearchInput
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.R
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.ImageDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.ui.theme.SplashBg
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * Accepts ViewModels as args instead of using
 * [com.goofy.goober.ui.viewmodel.ViewModelFactoryAmbientKt] in Composable
 * because of https://youtrack.jetbrains.com/issue/KT-41006
 */
@Composable
internal fun AstroApp(
    imageSearchViewModel: ImageSearchViewModel,
    detailsViewModel: DetailsViewModel,
    state: AstroState,
    onIntent: (AstroIntent) -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            Splash -> Splash(onIntent)
            ImageSearch -> ImageSearch(imageSearchViewModel, onIntent)
            is ImageDetails -> {
                DisplayingDetails(detailsViewModel, onIntent, state.image)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Splash(onIntent: (AstroIntent) -> Unit) {
    Surface(color = SplashBg, modifier = Modifier.fillMaxSize()) {
        val durationMillis = 800
        val animSpec = remember {
            tween<Float>(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing,
                delayMillis = 1_000
            )
        }
        val logoAlpha = animate(
            target = 1f,
            animSpec = animSpec,
            endListener = { onIntent(AstroIntent.ImageSearchResults) }
        )
        Image(
            asset = vectorResource(R.drawable.ic_nasa_vector_logo),
            modifier = Modifier.wrapContentSize(),
            contentScale = ContentScale.None,
            alpha = logoAlpha
        )
    }
}

@Composable
internal fun ImageSearch(
    viewModel: ImageSearchViewModel,
    onIntent: (AstroIntent) -> Unit
) {
    val state by viewModel.state.collectAsState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
            SearchInput { viewModel.consumeIntent(ImageResultsIntent.Search(it)) }
            Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
            ScrollableColumn(modifier = Modifier.padding(8.dp)) {
                state.images.forEach { image ->
                    ImageResultItem(image) { onIntent(AstroIntent.OpenDetails(image)) }
                }
            }
        }
    }
}

@Composable
internal fun DisplayingDetails(
    viewModel: DetailsViewModel,
    onIntent: (AstroIntent) -> Unit, image: Image
) {
    val state by viewModel.state.collectAsState()
    remember { viewModel.consumeIntent(DetailsIntent.LoadContent(image)) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val details = state.imageDetails
        when {
            details != null -> {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                ) {
                    CoilImage(
                        contentScale = ContentScale.Crop,
                        data = details.imageSizes.mediumSizeUrl,
                        fadeIn = true,
                        loading = {
                            Text(text = "Loading...")
                        },
                    )
                }
            }
            state.isLoading -> {
                Text(text = "Loading...")
            }
            state.hasError -> {
                Text(text = ":( Couldn't load")
            }
        }
    }
}
