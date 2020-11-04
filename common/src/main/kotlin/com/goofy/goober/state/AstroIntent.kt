package com.goofy.goober.state

import com.goofy.goober.api.model.Image

sealed class AstroIntent {
    object ImageSearchResults : AstroIntent()
    data class OpenDetails(val image: Image) : AstroIntent()
    object BackPress: AstroIntent()
}
