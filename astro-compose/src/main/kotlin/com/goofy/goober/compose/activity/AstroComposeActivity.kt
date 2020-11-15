package com.goofy.goober.compose.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import com.goofy.goober.compose.AstroApp
import com.goofy.goober.compose.navigation.BackPressedDispatcherAmbient
import com.goofy.goober.compose.theme.AstroAppTheme
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.compose.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel as koinViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class AstroComposeActivity : AppCompatActivity() {

    /**
     * Doing this instead of [com.goofy.goober.ui.viewmodel.ViewModelFactoryAmbientKt] in Composable
     * because of https://youtrack.jetbrains.com/issue/KT-41006
     */
    private val imageSearchViewModel by koinViewModel<ImageSearchViewModel>()
    private val detailsViewModel by koinViewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContent {
            Providers(BackPressedDispatcherAmbient provides this) {
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
    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
