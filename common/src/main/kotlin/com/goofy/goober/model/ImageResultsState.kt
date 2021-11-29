package com.goofy.goober.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.model.ImageResultsAction.Search
import com.goofy.goober.model.ImageResultsAction.ShowError
import com.goofy.goober.model.ImageResultsAction.ShowImages

data class ImageResultsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val noResults: Boolean = false,
    val images: List<Image> = emptyList()
)

fun ImageResultsState.reduce(intent: ImageResultsAction): ImageResultsState {
    return when (intent) {
        is ShowImages -> if (this.isLoading) imageResultsState(intent) else this
        is ShowError -> this.copy(isLoading = false, hasError = true, noResults = false)
        is Search -> if (intent.query.isNotBlank()) {
            this.copy(isLoading = true, hasError = false, noResults = false)
        } else {
            this
        }
    }
}

private fun imageResultsState(intent: ShowImages): ImageResultsState {
    val images = intent.images
    return if (images.isNotEmpty()) {
        ImageResultsState(images = images)
    } else {
        ImageResultsState(noResults = true)
    }
}

sealed class ImageResultsAction {
    data class Search(val query: String) : ImageResultsAction()
    data class ShowImages(val images: List<Image>) : ImageResultsAction()
    object ShowError : ImageResultsAction()
}
