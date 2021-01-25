package com.goofy.goober.interactor

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.usecase.EnqueueImageSearch
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.ImageSearchResults
import com.goofy.goober.api.util.Result
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.ImageDetails
import com.goofy.goober.model.ImageResultsIntent
import kotlinx.coroutines.flow.map

class AstroInteractor(
    private val imageSearchResults: ImageSearchResults,
    private val enqueueImageSearch: EnqueueImageSearch,
    private val getImageDetails: GetImageDetails
) {

    fun enqueueImageSearch(query: String) = enqueueImageSearch.invoke(query)

    fun produceImageSearchResultIntents() = imageSearchResults().map { it.asImageResultsIntent() }

    suspend fun produceDisplayDetailsIntent(image: Image): DetailsIntent {
        val result = getImageDetails(thumbUrl = image.thumbUrl, detailsUrl = image.detailUrl)
        return when (result) {
            is Result.Success -> DetailsIntent.DisplayContent(
                ImageDetails(
                    imageSizes = result.data,
                    description = image.description,
                    title = image.title
                )
            )

            is Result.Fail -> DetailsIntent.ShowError
        }
    }

    private fun Result<List<Image>>.asImageResultsIntent(): ImageResultsIntent {
        return when (this) {
            is Result.Success -> ImageResultsIntent.ShowImages(data)
            is Result.Fail -> ImageResultsIntent.ShowError
        }
    }
}
