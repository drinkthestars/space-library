package com.goofy.goober.compose.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
internal fun fadeOutAfter(
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
