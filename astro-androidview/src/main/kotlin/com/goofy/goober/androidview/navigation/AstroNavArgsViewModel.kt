package com.goofy.goober.androidview.navigation

import androidx.lifecycle.ViewModel
import com.goofy.goober.androidview.fragment.DetailsFragment
import com.goofy.goober.androidview.fragment.ImageSearchFragment
import com.goofy.goober.androidview.fragment.SplashFragment

internal class AstroNavArgsViewModel : ViewModel(), AstroFragmentArgs {
    var splashArgs: SplashFragment.Props? = null
    var imageSearchArgs: ImageSearchFragment.Props? = null
    var detailsArgs: DetailsFragment.Props? = null

    override val splashProps: SplashFragment.Props get() = checkNotNull(splashArgs)
    override val imageSearchProps: ImageSearchFragment.Props get() = checkNotNull(imageSearchArgs)
    override val detailsProps: DetailsFragment.Props get() = checkNotNull(detailsArgs)
}

internal interface AstroFragmentArgs :
    SplashFragment.FragmentArgs,
    ImageSearchFragment.FragmentArgs,
    DetailsFragment.FragmentArgs
