package com.goofy.goober.model

sealed class AstroIntent {

    object Initialize : AstroIntent()
    object EnterSearchParams : AstroIntent()
    object LoadMoreSearchResults : AstroIntent()
    object ShowMoreSearchResults : AstroIntent()
    object OpenDetails : AstroIntent()

}
