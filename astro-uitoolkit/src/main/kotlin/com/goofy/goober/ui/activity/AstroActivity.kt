package com.goofy.goober.ui.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.goofy.goober.R
import com.goofy.goober.ui.navigation.AstroNavArgsViewModel
import com.goofy.goober.ui.navigation.AstroAppRouter
import com.goofy.goober.viewmodel.AstroViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class AstroActivity : AppCompatActivity() {

    private val viewModel: AstroViewModel by viewModel()
    private val navArgsViewModel: AstroNavArgsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContentView(R.layout.main_activity)

        val router = AstroAppRouter(
            navController = navController(),
            navArgsViewModel = navArgsViewModel
        )

        viewModel.state
            .onEach { state ->
                router.route(state) { intent -> viewModel.consumeIntent(intent) }
            }.launchIn(lifecycleScope)
    }

    private fun navController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
