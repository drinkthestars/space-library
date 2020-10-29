package com.goofy.goober.interactor

import com.goofy.goober.api.usecase.SearchImages
import com.goofy.goober.model.Details
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.state.AstroIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AstroInteractor (
    private val searchImages: SearchImages
){

    suspend fun produceDisplayDetailsIntent(): DetailsIntent {
        delay(1_000)
        return DetailsIntent.DisplayContent(Details(url = "test", description = "", title = ""))
    }

    suspend fun produceImageResultsIntent(query: String): ImageResultsIntent {
        delay(1_000)
        return ImageResultsIntent.ShowImages(searchImages(query))
    }
}
