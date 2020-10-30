package com.goofy.goober.ui.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import com.goofy.goober.ui.AstroApp
import com.goofy.goober.ui.theme.AstroAppTheme
import com.goofy.goober.viewmodel.AstroViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class AstroComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContent {
            AstroAppTheme {
                val viewModel = viewModel<AstroViewModel>()
                val state by viewModel.state.collectAsState()

                AstroApp(
                    state = state,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
