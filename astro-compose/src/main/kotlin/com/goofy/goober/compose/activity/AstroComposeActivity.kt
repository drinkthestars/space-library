package com.goofy.goober.compose.activity

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goofy.goober.common.model.AstroNavAction
import com.goofy.goober.common.model.AstroNavState
import com.goofy.goober.common.model.ImageDetail
import com.goofy.goober.common.model.ImageResultsAction
import com.goofy.goober.common.model.ImageSearch
import com.goofy.goober.common.model.Splash
import com.goofy.goober.common.snapshot.DetailsViewModel
import com.goofy.goober.common.viewmodel.AstroNavViewModel
import com.goofy.goober.common.viewmodel.ImageSearchViewModel
import com.goofy.goober.compose.components.ImageDetails
import com.goofy.goober.compose.components.ImageSearch
import com.goofy.goober.compose.components.Splash
import com.goofy.goober.compose.theme.AstroAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel

internal class AstroComposeActivity : ComponentActivity() {

    private val imageSearchViewModel by koinViewModel<ImageSearchViewModel>()
    private val detailsViewModel by koinViewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContent {
            AstroAppTheme {
                val viewModel = viewModel<AstroNavViewModel>()
                AstroApp(
                    imageSearchViewModel = imageSearchViewModel,
                    detailsViewModel = detailsViewModel,
                    navState = viewModel.state,
                    onNavigate = { viewModel.dispatch(it) }
                )
            }
        }
    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    // TODO: Migrate to Hilt and remove VMs from args
    @Composable
    internal fun AstroApp(
        imageSearchViewModel: ImageSearchViewModel,
        detailsViewModel: DetailsViewModel,
        navState: AstroNavState,
        onNavigate: (AstroNavAction) -> Unit
    ) {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            when (navState) {
                is Splash -> Splash(onNavigate)
                is ImageSearch -> ImageSearch(imageSearchViewModel.state, onNavigate) {
                    imageSearchViewModel.dispatch(ImageResultsAction.Search(it))
                }
                is ImageDetail -> ImageDetails(
                    state = detailsViewModel.state,
                    image = navState.image,
                    onNavigate = onNavigate,
                    onAction = { detailsViewModel.dispatch(it) }
                )
            }
        }
    }
}
