package com.goofy.goober.compose.activity

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goofy.goober.compose.ImageDetails
import com.goofy.goober.compose.ImageSearch
import com.goofy.goober.compose.Splash
import com.goofy.goober.compose.theme.AstroAppTheme
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.ImageDetail
import com.goofy.goober.model.ImageResultsAction
import com.goofy.goober.model.ImageSearch
import com.goofy.goober.model.Splash
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class AstroComposeActivity : ComponentActivity() {

    private val imageSearchViewModel by koinViewModel<ImageSearchViewModel>()
    private val detailsViewModel by koinViewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContent {
            AstroAppTheme {
                val viewModel = viewModel<AstroViewModel>()
                val state by viewModel.state.collectAsState()
                AstroApp(
                    imageSearchViewModel = imageSearchViewModel,
                    detailsViewModel = detailsViewModel,
                    state = state,
                    onNavigate = { viewModel.consumeIntent(it) }
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
        state: AstroState,
        onNavigate: (AstroAction) -> Unit
    ) {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            when (state) {
                is Splash -> Splash(onNavigate)
                is ImageSearch -> ImageSearch(imageSearchViewModel.state, onNavigate) {
                    imageSearchViewModel.dispatch(ImageResultsAction.Search(it))
                }
                is ImageDetail -> ImageDetails(
                    state = detailsViewModel.state.collectAsState().value,
                    image = state.image,
                    onNavigate = onNavigate,
                    onAction = { detailsViewModel.dispatch(it) }
                )
            }
        }
    }
}
