package com.goofy.goober.state

import com.goofy.goober.api.model.Image

sealed class AstroIntent {
    data class ImageSearchResults(val query: String): AstroIntent()
    data class OpenDetails(val image: Image) : AstroIntent()
    object Back: AstroIntent()
}
