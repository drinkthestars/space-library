package com.goofy.goober

import android.app.Application
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.AstroUi
import com.goofy.goober.ui.state.AstroScreenStates
import com.goofy.goober.ui.viewmodel.AstroViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AstroApplication : Application() {

    private val appModule = module {

        factory<CoroutineScope> { GlobalScope }

        factory { ApplicationCoroutineScope(global = get()) }

        factory { AstroScreenStates() }

        factory { AstroInteractor() }

        factory { AstroUi() }

        viewModel {
            AstroViewModel(
                astroUi = get(),
                astroInteractor = get(),
                screenStates = get()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AstroApplication)
            modules(appModule)
        }
    }
}
