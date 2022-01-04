package com.goofy.goober.common.flow

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val flowModule = module {
    viewModel { AstroNavViewModel() }
    viewModel { DetailsViewModel(astroInteractor = get()) }
    viewModel { ImageSearchViewModel(astroInteractor = get()) }
}
