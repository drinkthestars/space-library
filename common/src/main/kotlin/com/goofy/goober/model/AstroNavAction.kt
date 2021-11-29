package com.goofy.goober.model

import com.goofy.goober.api.model.Image

sealed class AstroNavAction {
    object ToImageSearchResults : AstroNavAction()
    data class ToImageDetails(val image: Image) : AstroNavAction()
    object Back : AstroNavAction()
}
