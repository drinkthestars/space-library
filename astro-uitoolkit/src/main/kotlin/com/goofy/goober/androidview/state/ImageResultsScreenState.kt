package com.goofy.goober.androidview.state

import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This is logically the same as [ImageResultsScreenState] in the astro-compose package.
 * The only difference is that this uses [StateFlow] whereas that one uses the Compose
 * equivalent.
 */
class ImageResultsScreenState(
    initialQuery: String,
    initialImageResultsState: ImageResultsState
) {
    private val _imageResults = MutableStateFlow(initialImageResultsState)

    val query = MutableStateFlow(initialQuery)
    val imageResultsState: StateFlow<ImageResultsState> get() = _imageResults

    fun reduce(intent: ImageResultsIntent) {
        _imageResults.value = _imageResults.value.reduce(intent)
    }
}
