package com.goofy.goober.interactor

import com.goofy.goober.api.model.Image
import com.goofy.goober.api.usecase.EnqueueImageSearch
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.ImageSearchResults
import com.goofy.goober.api.util.Result
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.model.ImageDetails
import com.goofy.goober.model.ImageResultsAction
import kotlinx.coroutines.flow.map

class AstroInteractor(
    private val imageSearchResults: ImageSearchResults,
    private val enqueueImageSearch: EnqueueImageSearch,
    private val getImageDetails: GetImageDetails
) {

    fun enqueueImageSearch(query: String) = enqueueImageSearch.invoke(query)

    fun produceImageSearchResult() = imageSearchResults().map { it.asImageResultsAction() }

    suspend fun produceDisplayDetails(image: Image): DetailsAction {
        return when (val result = image.getDetails()) {
            is Result.Success -> DetailsAction.DisplayContent(
                ImageDetails(
                    imageSizes = result.data,
                    description = image.description,
                    title = image.title
                )
            )

            is Result.Fail -> DetailsAction.ShowError
        }
    }

    private suspend fun Image.getDetails() = getImageDetails(
        thumbUrl = thumbUrl,
        detailsUrl = detailUrl
    )

    private fun Result<List<Image>>.asImageResultsAction(): ImageResultsAction {
        return when (this) {
            is Result.Success -> ImageResultsAction.ShowImages(data)
            is Result.Fail -> ImageResultsAction.ShowError
        }
    }
}
