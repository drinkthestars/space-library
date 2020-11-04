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
import com.goofy.goober.ui.viewmodel.ProvideViewModelFactory
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@OptIn(ExperimentalCoroutinesApi::class)
class AstroComposeActivity : AppCompatActivity() {

    private val viewModelFactory: ViewModelFactory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        val factory = viewModelFactory
        setContent {
            ProvideViewModelFactory(factory) {
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
    }

//    override fun onBackPressed() {
//        if (!navigationViewModel.onBack()) {
//            super.onBackPressed()
//        }
//    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
