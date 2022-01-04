package com.goofy.goober.common.flow.model

import androidx.compose.runtime.mutableStateOf
import com.goofy.goober.common.model.ImageResultsAction
import com.goofy.goober.common.model.ImageResultsState
import com.goofy.goober.common.model.reduce
import com.goofy.goober.common.snapshot.model.ImageResultsScreenState
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This is logically the same as [ImageResultsScreenState] in the astro-compose package.
 * The only difference is that this
 * uses [kotlinx.coroutines.flow.MutableStateFlow] instead of Compose's [mutableStateOf]
 */
class ImageResultsScreenState(
    initialQuery: String,
    initialImageResultsState: ImageResultsState
) {
    val query = MutableStateFlow(initialQuery)
    val imageResultsState = MutableStateFlow(initialImageResultsState)

    fun update(action: ImageResultsAction) {
        imageResultsState.value = imageResultsState.value.reduce(action)
    }

    fun update(query: String) {
        this.query.value = query
    }
}
