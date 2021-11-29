package com.goofy.goober.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.moshi.defaultMoshi
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.goofy.goober.api.model.ApiImageResults
import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.ImageSizes
import com.goofy.goober.api.util.Result
import com.squareup.moshi.Types
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map


private const val BASE_URL = "https://images-api.nasa.gov"
private const val SEARCH_URL = "$BASE_URL/search"
private const val QUERY_PARAM = "q"

class ApiClient {

    private val mediaTypeParam = "media_type" to "image"
    private val detailUrlsAdapter by lazy {
        defaultMoshi.build().adapter<List<String>>(
            Types.newParameterizedType(List::class.java, String::class.java)
        )
    }
    private val searchRequests = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        FuelManager.instance.apply {
            basePath = BASE_URL
            Fuel.trace = true
        }
    }

    fun enqueueSearch(query: String) {
        searchRequests.tryEmit(query)
    }

    @OptIn(FlowPreview::class)
    fun searchResults(): Flow<Result<List<Image>>> {
        return searchRequests
            .debounce(500)
            .map { search(it) }
    }

    suspend fun search(query: String): Result<List<Image>> {
        val (results, error) = Fuel.get(SEARCH_URL, listOf(QUERY_PARAM to query, mediaTypeParam))
            .awaitObjectResult(moshiDeserializerOf(ApiImageResults::class.java))

        if (error != null) {
            println("Error loading images! ${error.message}")
            return Result.Fail
        }

        return Result.Success(results.asImages())
    }

    suspend fun getDetail(thumbUrl: String, detailsUrl: String): Result<ImageSizes> {
        val (results, error) = Fuel.get(detailsUrl)
            .awaitObjectResult(moshiDeserializerOf(detailUrlsAdapter))

        if (error != null || results == null) {
            println("Error loading image detail! Error = ${error?.message} | Results = $results")
            return Result.Fail
        }

        // TODO: Parse the various sizes better to choose the best size
        val imageDetail = ImageSizes(
            originalSizeUrl = results[0],
            mediumSizeUrl = thumbUrl,
        )

        return Result.Success(imageDetail)
    }

    private fun ApiImageResults?.asImages(): List<Image> {
        this ?: return emptyList()

        return this.collection.items.map {
            val data = it.data.first()
            Image(
                id = data.id,
                thumbUrl = it.links.first().thumbUrl,
                detailUrl = it.detailUrl,
                title = data.title,
                description = data.description,
            )
        }
    }
}
