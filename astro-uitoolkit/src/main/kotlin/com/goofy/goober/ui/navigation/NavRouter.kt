package com.goofy.goober.ui.navigation

import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.ui.viewmodel.AstroViewModel

internal class NavRouter(
    private val navController: NavController,
    private val viewModel: AstroViewModel,
) {
    // TODO: Handle Back Press

    fun <T: Any> navigateTo(screen: Screen<T>) {
        when(screen) {
            is Screen.Splash -> {
                viewModel.screenStates.updateWelcomeState(screen.prop)
            }
            is Screen.ImageResults -> {
                with(navController) {
                    if (currentDestination?.id == R.id.welcomeFragment) {
                        navigate(R.id.action_welcomeFragment_to_questionFragment)
                    }
                }
                viewModel.screenStates.updateQuestionState(screen.state)
            }
            is Screen.Details -> {
                navController.navigate(R.id.action_questionFragment_to_endFragment)
                viewModel.screenStates.updateEndState(screen.state)
            }
        }.let {}
    }
}
