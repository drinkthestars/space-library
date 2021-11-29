package com.goofy.goober.compose.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goofy.goober.api.model.Image
import com.goofy.goober.model.AstroNavAction
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.model.DetailsState

@Composable
internal fun ImageDetails(
    state: DetailsState,
    image: Image,
    onNavigate: (AstroNavAction) -> Unit,
    onAction: (DetailsAction) -> Unit
) {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onNavigate(AstroNavAction.Back)
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
private fun ImageLoadInProgress() {
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
private fun ImageLoadError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = ":( Couldn't load")
    }
}
