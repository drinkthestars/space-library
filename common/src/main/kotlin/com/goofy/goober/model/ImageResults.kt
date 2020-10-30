package com.goofy.goober.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.model.ImageResultsIntent.*

data class ImageResultsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val noResults: Boolean = false,
    val images: List<Image> = emptyList()
) {

    fun reduce(intent: ImageResultsIntent): ImageResultsState {
        return when (intent) {
            is ShowImages -> if (this.isLoading) imageResultsState(intent) else this
            is ShowError -> this.copy(isLoading = false, hasError = true, noResults = false)
            is Search -> this.copy(isLoading = true, hasError = false, noResults = false)
        }
    }

    private fun imageResultsState(intent: ShowImages): ImageResultsState {
        val images = intent.images
        return if (images.isNotEmpty()) {
            this.copy(isLoading = false, hasError = false, noResults = false, images = images)
        } else {
            this.copy(isLoading = false, hasError = false, noResults = true)
        }
    }
}

sealed class ImageResultsIntent {
    data class Search(val query: String) : ImageResultsIntent()
    data class ShowImages(val images: List<Image>) : ImageResultsIntent()
    object ShowError : ImageResultsIntent()
}
