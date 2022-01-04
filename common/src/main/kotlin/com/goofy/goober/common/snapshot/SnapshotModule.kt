package com.goofy.goober.common.snapshot

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val snapshotModule = module {
    viewModel { AstroNavViewModel() }
    viewModel { DetailsViewModel(astroInteractor = get()) }
    viewModel { ImageSearchViewModel(astroInteractor = get()) }
}
