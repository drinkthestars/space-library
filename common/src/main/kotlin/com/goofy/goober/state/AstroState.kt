package com.goofy.goober.state

import com.goofy.goober.api.model.Image

sealed class AstroState {
    abstract fun reduce(intent: AstroIntent): AstroState
}

data class Splash(val initialQuery: String) : AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> ImageSearch(intent.query)
            is AstroIntent.OpenDetails -> this
            is AstroIntent.Back -> this
        }
    }
}

data class ImageSearch(val query: String) : AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> this
            is AstroIntent.OpenDetails -> ImageDetails(query, intent.image)
            is AstroIntent.Back -> this
        }
    }
}

data class ImageDetails(val query: String, val image: Image) : AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> ImageSearch(intent.query)
            is AstroIntent.OpenDetails -> this
            is AstroIntent.Back -> ImageSearch(query)
        }
    }
}
