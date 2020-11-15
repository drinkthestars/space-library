package com.goofy.goober.state

import com.goofy.goober.api.model.Image

sealed class AstroState {
    abstract fun reduce(intent: AstroIntent): AstroState
}

object Splash : AstroState() {
    val route: String = "splash"

    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> ImageSearch
            is AstroIntent.OpenDetails -> this
            is AstroIntent.Back -> this
        }
    }
}

object ImageSearch : AstroState() {
    val route: String = "imageSearch"

    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> this
            is AstroIntent.OpenDetails -> ImageDetails(intent.image)
            is AstroIntent.Back -> this
        }
    }
}

data class ImageDetails(val image: Image) : AstroState() {
    companion object {
        val route: String = "imageSearch"
    }

    override fun reduce(intent: AstroIntent): AstroState {
        return when (intent) {
            is AstroIntent.ImageSearchResults -> ImageSearch
            is AstroIntent.OpenDetails -> this
            is AstroIntent.Back -> ImageSearch
        }
    }
}
