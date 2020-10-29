package com.goofy.goober.state

sealed class AstroIntent {
    object ImageSearchResults : AstroIntent()
    object OpenDetails : AstroIntent()
}
