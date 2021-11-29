package com.goofy.goober.androidview.navigation

import com.goofy.goober.R
import com.goofy.goober.androidview.fragment.DetailsFragment
import com.goofy.goober.androidview.fragment.ImageSearchFragment
import com.goofy.goober.androidview.fragment.SplashFragment
import com.goofy.goober.model.AstroNavAction
import com.goofy.goober.model.AstroNavAction.Back
import com.goofy.goober.model.AstroNavAction.ToImageSearchResults
import com.goofy.goober.model.AstroNavAction.ToImageDetails
import com.goofy.goober.model.AstroNavState
import com.goofy.goober.model.ImageDetail
import com.goofy.goober.model.ImageSearch
import com.goofy.goober.model.Splash

internal class AstroAppRouter(
    private val astroNavController: AstroNavController,
    private val navArgsViewModel: AstroNavArgsViewModel
) {

    fun route(
        astroNavState: AstroNavState,
        onAction: (AstroNavAction) -> Unit
    ) {
        astroNavState.routeInternal(onAction)
    }

    private fun AstroNavState.routeInternal(onAction: (AstroNavAction) -> Unit) {
        when (this) {
            is Splash -> {
                astroNavController {
                    navArgsViewModel.splashArgs = SplashFragment.Props(
                        onSplashDone = { onAction(ToImageSearchResults) },
                        onBack = { astroNavController.exit() },
                    )
                    navigate(R.id.splashFragment)
                }
            }
            is ImageSearch -> {
                astroNavController {
                    if (currentDestination?.id == R.id.splashFragment) {
                        navArgsViewModel.imageSearchArgs = ImageSearchFragment.Props(
                            onImageClick = { onAction(ToImageDetails(it)) },
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
