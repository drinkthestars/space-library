package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.model.Image

class SearchImages(
    private val apiClient: ApiClient
) {

    suspend operator fun invoke(query: String): List<Image> {
        return apiClient.search(query)
    }

}
