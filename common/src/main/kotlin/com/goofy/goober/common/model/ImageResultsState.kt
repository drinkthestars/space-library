package com.goofy.goober.common.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.common.model.ImageResultsAction.Search
import com.goofy.goober.common.model.ImageResultsAction.ShowError
import com.goofy.goober.common.model.ImageResultsAction.ShowImages

data class ImageResultsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val noResults: Boolean = false,
    val images: List<Image> = emptyList()
)

fun ImageResultsState.reduce(action: ImageResultsAction): ImageResultsState {
    return when (action) {
        is ShowImages -> if (this.isLoading) imageResultsState(action) else this
        is ShowError -> this.copy(isLoading = false, hasError = true, noResults = false)
        is Search -> if (action.query.isNotBlank()) {
            this.copy(isLoading = true, hasError = false, noResults = false)
        } else {
            this
        }
    }
}

private fun imageResultsState(action: ShowImages): ImageResultsState {
    val images = action.images
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
