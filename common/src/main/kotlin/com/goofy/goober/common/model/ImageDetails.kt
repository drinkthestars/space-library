package com.goofy.goober.common.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.ImageSizes

data class ImageDetails(val imageSizes: ImageSizes, val description: String, val title: String)

data class DetailsState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val imageDetails: ImageDetails? = null
) {
    fun reduce(action: DetailsAction): DetailsState {
        return when (action) {
            is DetailsAction.DisplayContent -> {
                if (isLoading) this.copy(
                    isLoading = false,
                    hasError = false,
                    imageDetails = action.imageDetails
                ) else this
            }
            DetailsAction.ShowError -> {
                this.copy(isLoading = false, hasError = true)
            }
            is DetailsAction.LoadContent -> {
                DetailsState(isLoading = true)
            }
        }
    }
}

sealed class DetailsAction {
    data class LoadContent(val image: Image) : DetailsAction()
    data class DisplayContent(val imageDetails: ImageDetails?) : DetailsAction()
    object ShowError : DetailsAction()
}
