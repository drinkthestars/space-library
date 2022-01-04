package com.goofy.goober.androidview

import android.app.Application
import com.goofy.goober.androidview.navigation.AstroNavArgsViewModel
import com.goofy.goober.common.common
import com.goofy.goober.common.flow.flowModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal class AstroApplication : Application() {

    private val appModule = module {
        viewModel { AstroNavArgsViewModel() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // https://youtrack.jetbrains.com/issue/KTOR-3575
            androidLogger(level = org.koin.core.logger.Level.ERROR)

            androidContext(this@AstroApplication)
            modules(common)
            modules(appModule)
            modules(flowModule)
        }
    }
}
