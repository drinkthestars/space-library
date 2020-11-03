package com.goofy.goober.model

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.ImageDetail

data class Details(val imageDetail: ImageDetail, val description: String, val title: String)

data class DetailsState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val details: Details? = null
) {
    fun reduce(intent: DetailsIntent): DetailsState {
       return when(intent) {
           is DetailsIntent.DisplayContent -> {
               if (isLoading) this.copy(isLoading = false, hasError = false, details = intent.details) else this
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
    data class DisplayContent(val details:Details?): DetailsIntent()
    object ShowError: DetailsIntent()
}
