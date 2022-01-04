package com.goofy.goober.compose

import android.app.Application
import com.goofy.goober.common.common
import com.goofy.goober.common.snapshot.snapshotModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class AstroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // https://youtrack.jetbrains.com/issue/KTOR-3575
            androidLogger(level = org.koin.core.logger.Level.ERROR)

            androidContext(this@AstroApplication)
            modules(common)
            modules(snapshotModule)
        }
    }
}
