package com.goofy.goober.model

data class Details(val url: String, val description: String, val title: String)

data class DetailsState(
    val isLoading: Boolean = false,
    val details: Details? = null
) {
    fun reduce(intent: DetailsIntent): DetailsState {
       return when(intent) {
           is DetailsIntent.DisplayContent -> {
               if (isLoading) this.copy(isLoading = false, details = intent.details) else this
           }
       }
    }
}

sealed class DetailsIntent {
    data class DisplayContent(val details:Details?): DetailsIntent()
}
