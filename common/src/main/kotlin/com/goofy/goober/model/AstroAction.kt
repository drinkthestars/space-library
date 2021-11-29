package com.goofy.goober.model

import com.goofy.goober.api.model.Image

sealed class AstroAction {
    object ImageSearchResults : AstroAction()
    data class OpenDetails(val image: Image) : AstroAction()
    object Back : AstroAction()
}
