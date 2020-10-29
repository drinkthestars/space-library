package com.goofy.goober.model

import com.goofy.goober.api.model.Image

data class ImageResultsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val images: List<Image> = emptyList()
) {

    fun reduce(intent: ImageResultsIntent): ImageResultsState {
        return when (intent) {
            is ImageResultsIntent.ShowImages -> {
                if (this.isLoading) {
                    this.copy(isLoading = false, hasError = false, images = intent.images)
                } else {
                    this
                }
            }
            ImageResultsIntent.ShowError -> this.copy(isLoading = false, hasError = true)
            is ImageResultsIntent.Search -> this.copy(isLoading = true, hasError = false)
        }
    }
}

sealed class ImageResultsIntent {
    data class Search(val query: String) : ImageResultsIntent()
    data class ShowImages(val images: List<Image>) : ImageResultsIntent()
    object ShowError : ImageResultsIntent()
}
