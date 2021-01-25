package com.goofy.goober

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.usecase.EnqueueImageSearch
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.ImageSearchResults
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.DetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
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

    viewModel { AstroViewModel() }

    viewModel { DetailsViewModel(astroInteractor = get()) }

    /**
     * Can't use until Kotlin 1.4.20 + Compose support
     * because of https://youtrack.jetbrains.com/issue/KT-41006
     */
//    factory { ViewModelFactory(astroInteractor = get()) }
}
