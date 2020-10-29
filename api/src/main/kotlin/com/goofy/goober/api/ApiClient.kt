package com.goofy.goober.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.goofy.goober.api.model.ApiImageResults
import com.goofy.goober.api.model.Image

private const val BASE_URL = "https://images-api.nasa.gov"
private const val SEARCH_URL = "$BASE_URL/search"
private const val QUERY_PARAM = "q"

class ApiClient {

    private val mediaTypeParam = "media_type" to "image"

    init {
        FuelManager.instance.basePath = BASE_URL
    }

    suspend fun search(query: String): List<Image> {
        val (results, error) =  Fuel.get(SEARCH_URL, listOf(QUERY_PARAM to query, mediaTypeParam))
            .awaitObjectResult(moshiDeserializerOf(ApiImageResults::class.java))

        if (error != null )  {
            println("Error loading images! ${error.message}")
            return emptyList()
        }

        return results.toImageResults()
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


