package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.model.ImageSizes
import com.goofy.goober.api.util.Result

class GetImageDetails(
    private val apiClient: ApiClient
) {

    suspend operator fun invoke(thumbUrl: String, detailsUrl: String):Result<ImageSizes> {
        return apiClient.getDetail(thumbUrl = thumbUrl, detailsUrl = detailsUrl)
    }

}
