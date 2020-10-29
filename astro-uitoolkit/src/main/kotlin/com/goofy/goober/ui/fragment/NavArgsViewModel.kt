package com.goofy.goober.ui.fragment

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class NavArgsViewModel : ViewModel(), AstroFragmentArgs {
    var splashArgs by Delegates.notNull<SplashFragment.Prop>()
    var imageSearchArgs by Delegates.notNull<ImageSearchFragment.Props>()
    var detailsArgs by Delegates.notNull<DetailsFragment.Props>()

    override val prop: SplashFragment.Prop get() = splashArgs
    override val imageSearchProps: ImageSearchFragment.Props get() = imageSearchArgs
    override val detailsProps: DetailsFragment.Props get() = detailsArgs
}

interface AstroFragmentArgs : SplashFragment.FragmentArgs,
    ImageSearchFragment.FragmentArgs,
    DetailsFragment.FragmentArgs
