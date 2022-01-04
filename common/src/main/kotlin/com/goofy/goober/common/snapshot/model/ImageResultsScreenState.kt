package com.goofy.goober.common.snapshot.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.goofy.goober.common.model.ImageResultsAction
import com.goofy.goober.common.model.ImageResultsState
import com.goofy.goober.common.model.reduce

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
        imageResultsState = imageResultsState.reduce(action)
    }

    fun update(query: String) {
        this.query = query
    }
}
