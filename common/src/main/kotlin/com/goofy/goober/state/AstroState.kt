package com.goofy.goober.state

import com.goofy.goober.api.model.Image

sealed class AstroState {
    abstract fun reduce(intent: AstroIntent): AstroState
}

object Splash: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> ImageSearch
            is AstroIntent.OpenDetails -> this
        }
    }
}

object ImageSearch: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> this
            is  AstroIntent.OpenDetails -> ImageDetails(intent.image)
        }
    }
}

data class ImageDetails(val image: Image): AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> ImageSearch
            is AstroIntent.OpenDetails -> this
        }
    }
}
