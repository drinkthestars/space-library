package com.goofy.goober.api.model

data class Image(
    val id: String,
    val thumbUrl: String,
    val detailUrl: String,
    val title: String,
    val description: String
)
