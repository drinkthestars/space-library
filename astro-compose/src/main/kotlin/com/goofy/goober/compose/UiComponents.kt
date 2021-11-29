package com.goofy.goober.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.R
import com.goofy.goober.compose.theme.ErrorBg
import com.goofy.goober.compose.theme.SplashBg
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.model.DetailsState
import com.goofy.goober.model.ImageResultsScreenState
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Splash(onNavigate: (AstroAction) -> Unit) {
    Surface(color = SplashBg, modifier = Modifier.fillMaxSize()) {
        SplashContent(onNavigate)
    }
}

@Composable
internal fun animatedOpacity(
    visible: Boolean,
    finishedListener: ((Float) -> Unit)? = null
): State<Float> {
    return animateFloatAsState(
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 500
        ),
        targetValue = if (visible) 1f else 0f,
        finishedListener = finishedListener
    )
}

@Composable
internal fun SplashContent(
    onAction: (AstroAction) -> Unit
) {
    val alpha = fadeOutAfter(duration = 1_700) {
        onAction(AstroAction.ImageSearchResults)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = alpha.value }
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Space Library",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 110.dp)
                    .align(Alignment.TopCenter)
            )
            Text(
                "nasa.gov/images",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color(0xAAFFFFFF),
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
internal fun ImageSearch(
    state: ImageResultsScreenState,
    onNavigate: (AstroAction) -> Unit,
    onSearch: (String) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(4.dp))
                SearchInputBar(
                    state.query,
                    onSearch = {
                        state.query = it
                        onSearch(it)
                    },
                    onQueryClear = { state.query = "" }
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                ) {
                    itemsIndexed(
                        items = state.imageResultsState.images,
                        itemContent = { _, item ->
                            ImageResultItem(item) { onNavigate(AstroAction.OpenDetails(it)) }
                        }
                    )
                }
            }
            ImageSearchResultsOverlay(state.imageResultsState)
        }
    }
}

@Composable
internal fun ImageDetails(
    state: DetailsState,
    image: Image,
    onNavigate: (AstroAction) -> Unit,
    onAction: (DetailsAction) -> Unit
) {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onNavigate(AstroAction.Back)
    }

    LaunchedEffect(1) { onAction(DetailsAction.LoadContent(image)) }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            state.imageDetails != null -> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    TransformableCoilImage(
                        data = state.imageDetails!!.imageSizes.originalSizeUrl,
                        contentDescription = state.imageDetails!!.title
                    )
                }
            }
            state.isLoading -> ImageLoadInProgress()
            state.hasError -> ImageLoadError()
        }
    }
}

@Composable
internal fun SearchInputBar(
    queryState: String,
    onSearch: (String) -> Unit,
    onQueryClear: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchInput(queryState) {
            onSearch(it)
        }
        Image(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterEnd)
                .padding(top = 6.dp, end = 12.dp)
                .clickable(onClick = onQueryClear),
            painter = painterResource(R.drawable.ic_clear),
            contentDescription = null
        )
    }
}

@Composable
internal fun SearchInput(
    textState: String,
    onTextFieldChange: (String) -> Unit
) {
    OutlinedTextField(
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusable(true),
        value = textState,
        onValueChange = onTextFieldChange
    )
}

@Composable
internal fun BoxScope.ImageSearchResultsOverlay(
    state: ImageResultsState
) {
    when {
        state.noResults -> {
            Column {
                Text(":(")
                Text("Nothing like that exists in the known universe...")
            }
        }
        state.isLoading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .background(Color(0x77123d40)),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color.Cyan,
                )
            }
        }
        state.hasError -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(ErrorBg)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error loading images",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
internal fun ImageResultItem(image: Image, onImageClick: (Image) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(onClick = { onImageClick(image) })
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(size = 5.dp)
        ) {
            Box {
                ImageContent(image)
                ImageTitleContent(image)
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun BoxScope.ImageTitleContent(image: Image) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .align(Alignment.BottomStart),
        text = image.title,
        color = Color.White,
        style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5f), fontSize = 16.sp),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ImageContent(image: Image) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = image.thumbUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null
        )
    }
}

@Composable
internal fun ImageLoadInProgress() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Composable
internal fun ImageLoadError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = ":( Couldn't load")
    }
}

@Composable
private fun fadeOutAfter(
    duration: Long,
    onFinished: () -> Unit
): Animatable<Float, AnimationVector1D> {
    var visible by remember(duration, onFinished) { mutableStateOf(true) }
    val alpha = remember(duration, onFinished) { Animatable(0f) }
    LaunchedEffect(duration, onFinished, visible) {
        if (visible) {
            delay(100)
        }
        alpha.animateTo(if (visible) 1f else 0f)
        if (!visible) {
            onFinished()
        }
    }
    LaunchedEffect(duration, onFinished) {
        delay(duration)
        visible = false
    }
    return alpha
}
