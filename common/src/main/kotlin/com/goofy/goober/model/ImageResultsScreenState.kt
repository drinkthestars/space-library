package com.goofy.goober.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot

@Stable
class ImageResultsScreenState(
    initialQuery: String,
    initialImageResultsState: ImageResultsState
) {
    var query by mutableStateOf(initialQuery)
        private set

    var imageResultsState by mutableStateOf(initialImageResultsState)
        private set

    fun update(action: ImageResultsAction) {
        withMutableSnapshot {
            imageResultsState = imageResultsState.reduce(action)
        }
    }

    fun update(query: String) {
        withMutableSnapshot {
            this.query = query
        }
    }
}
