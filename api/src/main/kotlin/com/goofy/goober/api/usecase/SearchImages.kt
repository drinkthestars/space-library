package com.goofy.goober.api.usecase

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.model.Image
import com.goofy.goober.api.util.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class SearchImages(
    private val apiClient: ApiClient
) {
    private var debouncePeriod: Long = 300
    private var searchDeferred: Deferred<Result<List<Image>>>? = null

    suspend operator fun invoke(query: String): Result<List<Image>> =
        coroutineScope {
            searchDeferred?.cancel()
            val deferredResult = async {
                delay(debouncePeriod)
                apiClient.search(query)
            }
            searchDeferred = deferredResult

            deferredResult.await()
        }
}
