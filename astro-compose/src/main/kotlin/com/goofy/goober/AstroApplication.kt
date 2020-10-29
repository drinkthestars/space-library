package com.goofy.goober

import android.app.Application
import com.goofy.goober.api.ApiClient
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.state.AstroUi
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AstroApplication : Application() {

    private val appModule = module {

    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AstroApplication)
            modules(common)
            modules(appModule)
        }
    }
}
