package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import com.goofy.goober.ui.AstroApp
import com.goofy.goober.ui.theme.AstroAppTheme
import com.goofy.goober.ui.viewmodel.AstroViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@OptIn(ExperimentalCoroutinesApi::class)
class AstroActivity : AppCompatActivity() {

    private val viewModel: AstroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstroAppTheme {
                AstroApp(
                    state = viewModel.state.collectAsState().value,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
