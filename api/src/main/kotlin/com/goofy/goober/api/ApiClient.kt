package com.goofy.goober.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.moshi.defaultMoshi
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.goofy.goober.api.model.ApiImageResults
import com.goofy.goober.api.model.Image
import com.goofy.goober.api.model.ImageDetail
import com.goofy.goober.api.util.Result
import com.squareup.moshi.Types


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

    init {
        FuelManager.instance.basePath = BASE_URL
    }

    suspend fun search(query: String): Result<List<Image>> {
        val (results, error) = Fuel.get(SEARCH_URL, listOf(QUERY_PARAM to query, mediaTypeParam))
            .awaitObjectResult(moshiDeserializerOf(ApiImageResults::class.java))

        if (error != null) {
            println("Error loading images! ${error.message}")
            return Result.Fail
        }

        return Result.Success(results.toImageResults())
    }

    suspend fun getDetail(url: String): Result<ImageDetail> {
        val (results, error) = Fuel.get(url)
            .awaitObjectResult(moshiDeserializerOf(detailUrlsAdapter))

        if (error != null || results == null || results.size < 4) {
            println("Error loading image detail! ${error?.message}")
            return Result.Fail
        }

        val imageDetail = ImageDetail(
            originalSizeUrl = results[0],
            largeSizeUrl = results[1],
            mediumSizeUrl = results[2],
            smallSizeUrl = results[3]
        )

        return Result.Success(imageDetail)
    }

    private fun ApiImageResults?.toImageResults(): List<Image> {
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


