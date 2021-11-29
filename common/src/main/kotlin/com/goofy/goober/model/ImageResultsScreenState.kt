package com.goofy.goober.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.flow.StateFlow

/**
 * This is logically the same as [ImageResultsScreenState] in the astro-uitoolkit package.
 * The only difference is that this uses Compose's [mutableStateOf] which is
 * equivalent to [StateFlow]
 */
@Stable
class ImageResultsScreenState(
    initialQuery: String,
    initialImageResultsState: ImageResultsState
) {
    var query by mutableStateOf(initialQuery)
    var imageResultsState by mutableStateOf(initialImageResultsState)
        private set

    fun update(intent: ImageResultsAction) {
        Snapshot.withMutableSnapshot {
            imageResultsState = imageResultsState.reduce(intent)
        }
    }
}
