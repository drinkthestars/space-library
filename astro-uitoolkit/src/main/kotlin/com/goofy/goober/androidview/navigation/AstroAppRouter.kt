package com.goofy.goober.androidview.navigation

import com.goofy.goober.R
import com.goofy.goober.androidview.fragment.DetailsFragment
import com.goofy.goober.androidview.fragment.ImageSearchFragment
import com.goofy.goober.androidview.fragment.SplashFragment
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.AstroAction.Back
import com.goofy.goober.model.AstroAction.ImageSearchResults
import com.goofy.goober.model.AstroAction.OpenDetails
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.ImageDetail
import com.goofy.goober.model.ImageSearch
import com.goofy.goober.model.Splash

internal class AstroAppRouter(
    private val astroNavController: AstroNavController,
    private val navArgsViewModel: AstroNavArgsViewModel
) {

    fun route(
        astroState: AstroState,
        onAction: (AstroAction) -> Unit
    ) {
        astroState.routeInternal(onAction)
    }

    private fun AstroState.routeInternal(onAction: (AstroAction) -> Unit) {
        when (this) {
            is Splash -> {
                astroNavController {
                    navArgsViewModel.splashArgs = SplashFragment.Props(
                        onSplashDone = { onAction(ImageSearchResults) },
                        onBack = { astroNavController.exit() },
                    )
                    navigate(R.id.splashFragment)
                }
            }
            is ImageSearch -> {
                astroNavController {
                    if (currentDestination?.id == R.id.splashFragment) {
                        navArgsViewModel.imageSearchArgs = ImageSearchFragment.Props(
                            onImageClick = { onAction(OpenDetails(it)) },
                            onBack = { astroNavController.exit() }
                        )
                        navigate(R.id.imageSearchFragment)
                    } else if (currentDestination?.id == R.id.detailsFragment) {
                        popBackStack()
                    }
                }
            }
            is ImageDetail -> {
                astroNavController {
                    if (currentDestination?.id != R.id.detailsFragment) {
                        navArgsViewModel.detailsArgs = DetailsFragment.Props(
                            onBack = { onAction(Back) },
                            image = image
                        )
                        navigate(R.id.detailsFragment)
                    }
                }
            }
        }
    }
}
