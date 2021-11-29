package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient

class ImageSearchResults(
    private val apiClient: ApiClient
) {
    operator fun invoke() = apiClient.searchResults()
}
