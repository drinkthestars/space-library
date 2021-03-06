package com.goofy.goober.androidview.activity

import com.goofy.goober.R
import com.goofy.goober.androidview.fragment.DetailsFragment
import com.goofy.goober.androidview.fragment.ImageSearchFragment
import com.goofy.goober.androidview.fragment.SplashFragment
import com.goofy.goober.androidview.util.AstroNavArgsViewModel
import com.goofy.goober.androidview.util.AstroNavController
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.ImageDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash

internal class AstroAppRouter(
    private val astroNavController: AstroNavController,
    private val navArgsViewModel: AstroNavArgsViewModel
) {

    fun route(
        astroState: AstroState,
        onIntent: (AstroIntent) -> Unit
    ) {
        astroState.routeInternal(onIntent)
    }

    private fun AstroState.routeInternal(onIntent: (AstroIntent) -> Unit) {
        when (this) {
            is Splash -> {
                astroNavController {
                    navArgsViewModel.splashArgs = SplashFragment.Props(
                        onSplashDone = { onIntent(AstroIntent.ImageSearchResults) },
                        onBack = { astroNavController.exit() },
                    )
                    navigate(R.id.splashFragment)
                }
            }
            is ImageSearch -> {
                astroNavController {
                    if (currentDestination?.id == R.id.splashFragment) {
                        navArgsViewModel.imageSearchArgs = ImageSearchFragment.Props(
                            onImageClick = { onIntent(AstroIntent.OpenDetails(it)) },
                            onBack = { astroNavController.exit() }
                        )
                        navigate(R.id.imageSearchFragment)
                    } else if (currentDestination?.id == R.id.detailsFragment) {
                        popBackStack()
                    }
                }
            }
            is ImageDetails -> {
                astroNavController {
                    if (currentDestination?.id != R.id.detailsFragment) {
                        navArgsViewModel.detailsArgs = DetailsFragment.Props(
                            onBack = { onIntent(AstroIntent.Back) },
                            image = image
                        )
                        navigate(R.id.detailsFragment)
                    }
                }
            }
        }
    }
}
