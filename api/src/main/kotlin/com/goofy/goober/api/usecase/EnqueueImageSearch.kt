package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient

class EnqueueImageSearch(
    private val apiClient: ApiClient
) {
    operator fun invoke(query: String) = apiClient.enqueueSearch(query)
}
