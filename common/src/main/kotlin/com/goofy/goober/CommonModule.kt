package com.goofy.goober

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.usecase.GetImageDetails
import com.goofy.goober.api.usecase.SearchImages
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.viewmodel.AstroViewModel
import com.goofy.goober.viewmodel.DetailsViewModel
import com.goofy.goober.viewmodel.ImageSearchViewModel
import com.goofy.goober.viewmodel.ViewModelFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val common = module {

    factory { ApiClient() }

    factory { SearchImages(apiClient = get()) }

    factory { GetImageDetails(apiClient = get()) }

    factory { AstroInteractor(searchImages = get(), getImageDetails = get()) }

    viewModel { AstroViewModel() }

    viewModel { DetailsViewModel(astroInteractor = get()) }

    viewModel { ImageSearchViewModel(astroInteractor = get()) }

    factory { ViewModelFactory(astroInteractor = get()) }
}
