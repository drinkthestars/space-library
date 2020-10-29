package com.goofy.goober.ui.uitoolkit

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.goofy.goober.R
import com.goofy.goober.ui.navigation.AstroAppRouter
import com.goofy.goober.ui.fragment.AstroFragmentArgs
import com.goofy.goober.ui.fragment.FragmentArgsProvider
import com.goofy.goober.ui.fragment.NavArgsViewModel
import com.goofy.goober.viewmodel.AstroViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class AstroActivity : AppCompatActivity(), FragmentArgsProvider<AstroFragmentArgs> {

    private val viewModel: AstroViewModel by viewModel()
    private val navArgsViewModel: NavArgsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val router = AstroAppRouter(
            navController = navController,
            navArgsViewModel = navArgsViewModel
        )

        viewModel.state
            .onEach { state ->
                router.route(state) { intent -> viewModel.consumeIntent(intent) }
            }.launchIn(lifecycleScope)
    }

    override fun provideFragmentArgs(): AstroFragmentArgs = navArgsViewModel
}
