package com.goofy.goober.interactor

import com.goofy.goober.model.AstroIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AstroInteractor {

    suspend fun produceInitIntent(): AstroIntent = withContext(Dispatchers.IO) {
        // Do some work
        delay(2_000)

        AstroIntent.Initialize
    }
}
