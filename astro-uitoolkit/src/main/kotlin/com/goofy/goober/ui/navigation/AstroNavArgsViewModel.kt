package com.goofy.goober.ui.navigation

import com.goofy.goober.ui.fragment.DetailsFragment
import com.goofy.goober.ui.fragment.ImageSearchFragment
import com.goofy.goober.ui.fragment.SplashFragment
import com.goofy.goober.ui.util.NavArgsViewModel
import kotlin.properties.Delegates

class AstroNavArgsViewModel : NavArgsViewModel(), AstroFragmentArgs {
    var splashArgs by Delegates.notNull<SplashFragment.Prop>()
    var imageSearchArgs by Delegates.notNull<ImageSearchFragment.Props>()
    var detailsArgs by Delegates.notNull<DetailsFragment.Props>()

    override val prop: SplashFragment.Prop get() = splashArgs
    override val imageSearchProps: ImageSearchFragment.Props get() = imageSearchArgs
    override val detailsProps: DetailsFragment.Props get() = detailsArgs
}

interface AstroFragmentArgs :
    SplashFragment.FragmentArgs,
    ImageSearchFragment.FragmentArgs,
    DetailsFragment.FragmentArgs
