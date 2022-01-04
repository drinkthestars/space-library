package com.goofy.goober.common.model

import com.goofy.goober.api.model.Image

sealed class AstroNavState {
    abstract fun reduce(action: AstroNavAction): AstroNavState
}

object Splash : AstroNavState() {
    val route: String = "splash"

    override fun reduce(action: AstroNavAction): AstroNavState {
        return when (action) {
            is AstroNavAction.ToImageSearchResults -> ImageSearch
            else -> this
        }
    }
}

object ImageSearch : AstroNavState() {
    val route: String = "imageSearch"

    override fun reduce(action: AstroNavAction): AstroNavState {
        return when (action) {
            is AstroNavAction.ToImageDetails -> ImageDetail(action.image)
            else -> this
        }
    }
}

data class ImageDetail(val image: Image) : AstroNavState() {
    companion object {
        val route: String = "imageSearch"
    }

    override fun reduce(action: AstroNavAction): AstroNavState {
        return when (action) {
            is AstroNavAction.ToImageSearchResults,
            is AstroNavAction.Back -> ImageSearch
            is AstroNavAction.ToImageDetails -> this
        }
    }
}
