package com.goofy.goober.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
internal fun TransformableCoilImage(
    data: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val coroutineScope = rememberCoroutineScope()
    // let's create a modifier state to specify how to update our UI state defined above
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        // note: scale goes by factor, not an absolute difference, so we need to multiply it
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        modifier
            // apply pan offset state as a layout transformation before other modifiers
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            // add transformable to listen to multitouch transformation events after offset
            .transformable(state = state)
            // optional for example: add double click to zoom
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        coroutineScope.launch { state.animateZoomBy(4f) }
                    }
                )
            }
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            alignment = alignment,
            contentScale = contentScale,
            colorFilter = colorFilter,
            painter = rememberImagePainter(
                data = data,
                builder = {
                    builder().apply {
                        crossfade(true)
                    }
                }
            ),
            contentDescription = contentDescription,
            // apply other transformations like rotation and zoom on the pizza slice emoji
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
        )
    }
}
