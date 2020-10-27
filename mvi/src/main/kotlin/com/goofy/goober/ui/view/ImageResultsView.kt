package com.goofy.goober.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class ImageResultsView(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    init {
        orientation = VERTICAL
        setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
    }

    fun setState(state: State?) {
    }

    data class State(
        val images: List<String>,
        val clickListener: (String) -> Unit
    )
}
