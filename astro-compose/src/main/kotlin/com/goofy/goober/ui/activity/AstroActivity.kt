package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import com.goofy.goober.ui.AstroApp
import com.goofy.goober.ui.theme.AstroAppTheme
import com.goofy.goober.viewmodel.AstroViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class AstroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstroAppTheme {
                val viewModel = viewModel<AstroViewModel>()
                AstroApp(
                    state = viewModel.state.collectAsState().value,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
