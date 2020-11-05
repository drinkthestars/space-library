package com.goofy.goober.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.ImageSizes

data class ImageDetails(val imageSizes: ImageSizes, val description: String, val title: String)

data class DetailsState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val imageDetails: ImageDetails? = null
) {
    fun reduce(intent: DetailsIntent): DetailsState {
       return when(intent) {
           is DetailsIntent.DisplayContent -> {
               if (isLoading) this.copy(isLoading = false, hasError = false, imageDetails = intent.imageDetails) else this
           }
           DetailsIntent.ShowError -> {
               this.copy(isLoading = false, hasError = true)
           }
           is DetailsIntent.LoadContent -> {
               DetailsState(isLoading = true)
           }
       }
    }
}

sealed class DetailsIntent {
    data class LoadContent(val image: Image): DetailsIntent()
    data class DisplayContent(val imageDetails:ImageDetails?): DetailsIntent()
    object ShowError: DetailsIntent()
}
