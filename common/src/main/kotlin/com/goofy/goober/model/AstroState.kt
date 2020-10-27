package com.goofy.goober.model

sealed class AstroState {

    abstract fun reduce(intent: AstroIntent): AstroState

}

object SplashState: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.Initialize -> TODO()
            AstroIntent.EnterSearchParams -> TODO()
            AstroIntent.LoadMoreSearchResults -> TODO()
            AstroIntent.ShowMoreSearchResults -> TODO()
            AstroIntent.OpenDetails -> TODO()
        }
    }
}

object ImageSearchState: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.Initialize -> TODO()
            AstroIntent.EnterSearchParams -> TODO()
            AstroIntent.LoadMoreSearchResults -> TODO()
            AstroIntent.ShowMoreSearchResults -> TODO()
            AstroIntent.OpenDetails -> TODO()
        }
    }
}

object DisplayingDetailsState: AstroState() {
    override fun reduce(intent: AstroIntent): AstroState {
        return when(intent) {
            AstroIntent.Initialize -> TODO()
            AstroIntent.EnterSearchParams -> TODO()
            AstroIntent.LoadMoreSearchResults -> TODO()
            AstroIntent.ShowMoreSearchResults -> TODO()
            AstroIntent.OpenDetails -> TODO()
        }
    }
}
