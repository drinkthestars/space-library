package com.goofy.goober.ui.navigation

import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.ImageDetails
import com.goofy.goober.state.ImageSearch
import com.goofy.goober.state.Splash
import com.goofy.goober.ui.fragment.DetailsFragment
import com.goofy.goober.ui.fragment.ImageSearchFragment
import com.goofy.goober.ui.fragment.SplashFragment

internal class AstroAppRouter(
    private val navController: NavController,
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
            Splash -> {
                navArgsViewModel.splashArgs = SplashFragment.Prop {
                    onIntent(AstroIntent.ImageSearchResults)
                }
            }
            ImageSearch -> {
                navArgsViewModel.imageSearchArgs = ImageSearchFragment.Props(
                    onImageClick = { onIntent(AstroIntent.OpenDetails(it)) }
                )
                with(navController) {
                    if (currentDestination?.id == R.id.splashFragment) {
                        navigate(R.id.showImageSearchFragmentAction)
                    }
                }
            }
            is ImageDetails -> {
                navArgsViewModel.detailsArgs = DetailsFragment.Props(
                    onBackPressed = { },
                    initialIntent = DetailsIntent.DisplayContent(null)
                )
                navController.navigate(R.id.showDetailsFragmentAction)
            }
        }.let {}
    }
}
