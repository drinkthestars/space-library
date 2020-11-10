package com.goofy.goober.ui.activity

import com.goofy.goober.R
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.ImageDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.ui.fragment.DetailsFragment
import com.goofy.goober.ui.fragment.ImageSearchFragment
import com.goofy.goober.ui.fragment.SplashFragment
import com.goofy.goober.ui.navigation.AstroNavArgsViewModel
import com.goofy.goober.ui.navigation.AstroNavController

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
                        onSplashDone = { onIntent(AstroIntent.ImageSearchResults(initialQuery)) },
                        onBack = { astroNavController.exit() },
                    )
                }
            }
            is ImageSearch -> {
                astroNavController {
                    if (currentDestination?.id != R.id.imageSearchFragment) {
                        navArgsViewModel.imageSearchArgs = ImageSearchFragment.Props(
                            onImageClick = { onIntent(AstroIntent.OpenDetails(it)) },
                            onBack = { astroNavController.exit() },
                            query = query
                        )
                        navigate(R.id.imageSearchFragment)
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
