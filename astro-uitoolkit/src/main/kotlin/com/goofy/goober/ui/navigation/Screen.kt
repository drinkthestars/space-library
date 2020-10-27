package com.goofy.goober.ui.navigation

import com.goofy.goober.ui.fragment.DetailsFragment
import com.goofy.goober.ui.fragment.SplashFragment
import com.goofy.goober.ui.view.ImageResultsView

sealed class Screen<out STATE: Any> {
    abstract val state: STATE

    data class Splash(override val state: SplashFragment.Prop): Screen<SplashFragment.Prop>()

    data class ImageResults(override val state: ImageResultsView.State): Screen<ImageResultsView.State>()

    data class Details(override val state: DetailsFragment.State): Screen<DetailsFragment.State>()
}
