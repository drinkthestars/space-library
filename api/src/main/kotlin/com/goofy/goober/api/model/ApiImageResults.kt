package com.goofy.goober.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiImageResults(
    @Json(name = "collection") val collection: Collection
) {

    @JsonClass(generateAdapter = true)
    data class Collection(
        @Json(name = "items") val items: List<Item>
    ) {

        @JsonClass(generateAdapter = true)
        data class Item(
            @Json(name = "href") val detailUrl: String,
            @Json(name = "links") val links: List<Link>,
            @Json(name = "data") val data: List<Data>,
        ) {
            @JsonClass(generateAdapter = true)
            data class Link(
                @Json(name = "href") val thumbUrl: String,
            )

            @JsonClass(generateAdapter = true)
            data class Data(
                @Json(name = "description") val description: String,
                @Json(name = "title") val title: String,
                @Json(name = "nasa_id") val id: String,
            )
        }
    }
}

