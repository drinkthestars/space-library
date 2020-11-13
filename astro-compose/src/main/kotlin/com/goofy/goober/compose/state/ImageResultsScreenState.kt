package com.goofy.goober.compose.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.flow.StateFlow

/**
 * This is logically the same as [ImageResultsScreenState] in the astro-uitoolkit package.
 * The only difference is that this uses Compose's [mutableStateOf] which is
 * equivalent to [StateFlow]
 */
class ImageResultsScreenState(
    initialQuery: String,
    initialImageResultsState: ImageResultsState
) {
    var query by mutableStateOf(TextFieldValue(initialQuery))
    var imageResultsState by mutableStateOf(initialImageResultsState)
        private set

    fun reduce(intent: ImageResultsIntent) {
        imageResultsState = imageResultsState.reduce(intent)
    }
}
