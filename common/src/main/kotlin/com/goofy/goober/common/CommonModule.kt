package com.goofy.goober.common

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.usecase.EnqueueImageSearch
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.ImageSearchResults
import com.goofy.goober.common.interactor.AstroInteractor
import org.koin.dsl.module

val common = module {

    single { ApiClient() }

    factory { EnqueueImageSearch(apiClient = get()) }

    factory { ImageSearchResults(apiClient = get()) }

    factory { GetImageDetails(apiClient = get()) }

    factory {
        AstroInteractor(
            enqueueImageSearch = get(),
            getImageDetails = get(),
            imageSearchResults = get()
        )
    }

    /**
     * Can't use until Kotlin 1.4.20 + Compose support
     * because of https://youtrack.jetbrains.com/issue/KT-41006
     */
//    factory { ViewModelFactory(astroInteractor = get()) }
}
