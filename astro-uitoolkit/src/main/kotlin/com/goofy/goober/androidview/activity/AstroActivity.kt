package com.goofy.goober.androidview.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.goofy.goober.R
import com.goofy.goober.androidview.navigation.AstroAppRouter
import com.goofy.goober.androidview.navigation.AstroNavArgsViewModel
import com.goofy.goober.androidview.navigation.AstroNavController
import com.goofy.goober.viewmodel.AstroViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AstroActivity : AppCompatActivity() {

    private val viewModel: AstroViewModel by viewModel()
    private val navArgsViewModel: AstroNavArgsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullscreen()
        setContentView(R.layout.main_activity)

        val router = AstroAppRouter(
            astroNavController = navController(),
            navArgsViewModel = navArgsViewModel
        )

        lifecycleScope.launchWhenStarted {
            snapshotFlow { viewModel.state }.collect { state ->
                router.route(state) { viewModel.dispatch(it) }
            }
        }
    }

    private fun navController(): AstroNavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        return AstroNavController(navHostFragment.navController, onExit = { finish() })
    }

    private fun fullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
