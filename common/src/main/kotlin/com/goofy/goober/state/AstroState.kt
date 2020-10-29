package com.goofy.goober.state

sealed class AstroState {
    abstract fun reduce(intent: AstroIntent): AstroState
}

object Splash: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> ImageSearch
            AstroIntent.OpenDetails -> this
        }
    }
}

object ImageSearch: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> this
            AstroIntent.OpenDetails -> DisplayingDetails("")
        }
    }
}

data class DisplayingDetails(val url: String): AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.ImageSearchResults -> ImageSearch
            AstroIntent.OpenDetails -> this
        }
    }
}
