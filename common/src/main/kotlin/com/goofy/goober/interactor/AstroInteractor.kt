package com.goofy.goober.interactor

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.SearchImages
import com.goofy.goober.api.util.Result
import com.goofy.goober.model.ImageDetails
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.ImageResultsIntent

class AstroInteractor(
    private val searchImages: SearchImages,
    private val getImageDetails: GetImageDetails
) {

    suspend fun produceDisplayDetailsIntent(image: Image): DetailsIntent {
        return when (val result = getImageDetails(thumbUrl = image.thumbUrl, detailsUrl = image.detailUrl)) {
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

    suspend fun produceImageResultsIntent(query: String): ImageResultsIntent {
        return when (val result = searchImages(query)) {
            is Result.Success -> ImageResultsIntent.ShowImages(result.data)
            is Result.Fail -> ImageResultsIntent.ShowError
        }
    }
}
