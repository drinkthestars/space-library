package com.goofy.goober.model

import com.goofy.goober.api.model.Image

sealed class AstroState {
    abstract fun reduce(intent: AstroAction): AstroState
}

object Splash : AstroState() {
    val route: String = "splash"

    override fun reduce(intent: AstroAction): AstroState {
        return when (intent) {
            is AstroAction.ImageSearchResults -> ImageSearch
            is AstroAction.OpenDetails -> this
            is AstroAction.Back -> this
        }
    }
}

object ImageSearch : AstroState() {
    val route: String = "imageSearch"

    override fun reduce(intent: AstroAction): AstroState {
        return when (intent) {
            is AstroAction.ImageSearchResults -> this
            is AstroAction.OpenDetails -> ImageDetail(intent.image)
            is AstroAction.Back -> this
        }
    }
}

data class ImageDetail(val image: Image) : AstroState() {
    companion object {
        val route: String = "imageSearch"
    }

    override fun reduce(intent: AstroAction): AstroState {
        return when (intent) {
            is AstroAction.ImageSearchResults -> ImageSearch
            is AstroAction.OpenDetails -> this
            is AstroAction.Back -> ImageSearch
        }
    }
}
