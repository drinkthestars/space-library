package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goofy.goober.interactor.AstroInteractor

class ViewModelFactory (
    private val astroInteractor: AstroInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(astroInteractor) as T
            }
            modelClass.isAssignableFrom(ImageSearchViewModel::class.java) -> {
                ImageSearchViewModel(astroInteractor) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}
