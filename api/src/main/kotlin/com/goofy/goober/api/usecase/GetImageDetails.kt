package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.model.ImageDetail
import com.goofy.goober.api.util.Result

class GetImageDetails(
    private val apiClient: ApiClient
) {

    suspend operator fun invoke(url: String):Result<ImageDetail> {
        return apiClient.getDetail(url)
    }

}
