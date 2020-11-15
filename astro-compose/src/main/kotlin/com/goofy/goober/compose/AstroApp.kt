package com.goofy.goober.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.R
import com.goofy.goober.compose.navigation.backPressHandler
import com.goofy.goober.compose.theme.SplashBg
import com.goofy.goober.compose.viewmodel.ImageSearchViewModel
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.ImageDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.viewmodel.DetailsViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * Accepts ViewModels as args instead of using
 * [ViewModelFactoryAmbientKt] in Composable
 * because of https://youtrack.jetbrains.com/issue/KT-41006
 */
@Composable
internal fun AstroApp(
    imageSearchViewModel: ImageSearchViewModel,
    detailsViewModel: DetailsViewModel,
    state: AstroState,
    onNavigate: (AstroIntent) -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            is Splash -> Splash(onNavigate)
            is ImageSearch -> ImageSearch(imageSearchViewModel, onNavigate)
            is ImageDetails -> ImageDetails(detailsViewModel, state.image, onNavigate)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Splash(onNavigate: (AstroIntent) -> Unit) {
    Surface(color = SplashBg, modifier = Modifier.fillMaxSize()) {
        val opacity = animatedOpacity(
            animation = tween(
                easing = LinearEasing,
                delayMillis = 100,
                durationMillis = 1200
            ),
            visible = true,
            onAnimationFinish = {
                onNavigate(AstroIntent.ImageSearchResults)
            }
        )
        SplashContent(opacity)
    }
}

@Composable
internal fun SplashContent(opacity: AnimatedFloat) {
    Box(
        alignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().drawLayer(alpha = opacity.value)
    ) {
        Image(
            asset = imageResource(R.raw.splash),
            modifier = Modifier.wrapContentSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0x80000000)),
            alignment = Alignment.Center
        ) {
            Text(
                "Space Library",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 110.dp).align(Alignment.TopCenter)
            )
            Text(
                "nasa.gov/images",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color(0xAAFFFFFF),
                modifier = Modifier.padding(bottom = 80.dp).align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
internal fun ImageSearch(
    viewModel: ImageSearchViewModel,
    onNavigate: (AstroIntent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(4.dp))
                SearchInputBar(
                    viewModel.state.query,
                    onSearch = {
                        viewModel.state.query = it
                        viewModel.consumeIntent(ImageResultsIntent.Search(it.text))
                    },
                    onQueryClear = { viewModel.state.query = TextFieldValue("") }
                )
                Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(4.dp))
                LazyColumnFor(
                    items = viewModel.state.imageResultsState.images,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                ) {
                    ImageResultItem(it) { onNavigate(AstroIntent.OpenDetails(it)) }
                }
            }
            ImageSearchResultsOverlay(viewModel.state.imageResultsState)
        }
    }
}

@Composable
internal fun ImageDetails(
    viewModel: DetailsViewModel,
    image: Image,
    onNavigate: (AstroIntent) -> Unit
) {
    backPressHandler(onBackPressed = { onNavigate(AstroIntent.Back) })
    val state by viewModel.state.collectAsState()
    val details = state.imageDetails
    remember { viewModel.consumeIntent(DetailsIntent.LoadContent(image)) }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            details != null -> {
                Box(
                    alignment = Alignment.Center
                ) {
                    CoilImage(
                        contentScale = ContentScale.Crop,
                        data = details.imageSizes.mediumSizeUrl,
                        fadeIn = true,
                        loading = { ImageLoadInProgress() },
                        error = { ImageLoadError() }
                    )
                }
            }
            state.isLoading -> ImageLoadInProgress()
            state.hasError -> ImageLoadError()
        }
    }
}
