package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.goofy.goober.R
import com.goofy.goober.ui.AstroRenderer
import com.goofy.goober.ui.navigation.NavRouter
import com.goofy.goober.ui.state.FragmentStateProvider
import com.goofy.goober.ui.state.PizzaChildFragmentStates
import com.goofy.goober.ui.viewmodel.AstroViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class AstroActivity : AppCompatActivity(), FragmentStateProvider<PizzaChildFragmentStates> {

    private val viewModel: AstroViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        val navRouter = NavRouter(
            navController = findNavController(this@AstroActivity, R.id.navHostFragment),
            viewModel = viewModel
        )

        val pizzaRenderer = AstroRenderer(navRouter)

        viewModel.state
            .onEach { state ->
                pizzaRenderer.render(state) { intent ->
                    viewModel.consumeIntent(intent)
                }
            }.launchIn(lifecycleScope)
    }

    override fun provideFragmentStates(): PizzaChildFragmentStates {
        return viewModel.screenStates
    }
}
