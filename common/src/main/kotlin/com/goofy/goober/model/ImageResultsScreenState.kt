package com.goofy.goober.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot

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
        Snapshot.withMutableSnapshot {
            imageResultsState = imageResultsState.reduce(action)
        }
    }

    fun update(query: String) {
        Snapshot.withMutableSnapshot {
            this.query = query
        }
    }
}
