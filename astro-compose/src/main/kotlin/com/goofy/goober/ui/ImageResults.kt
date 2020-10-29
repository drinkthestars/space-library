package com.goofy.goober.ui

import androidx.compose.runtime.mutableStateListOf

class ImagesResult(
    images: List<String>
) {
    private val _images: MutableList<String> = mutableStateListOf(*images.toTypedArray())

    val images: List<String> = _images

    fun addImages(url: String) {
        _images.add(url)
    }
}
