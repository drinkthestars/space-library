package com.goofy.goober.androidview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.androidview.state.ImageResultsScreenState
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.ImageResultsState
import com.goofy.goober.model.ImageResultsIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * This is logically the same as [ImageSearchViewModel] in the astro-compose package.
 * The only difference is that this [ImageResultsScreenState] here
 * uses [StateFlow]
 */
internal class ImageSearchViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = ImageResultsScreenState(
        initialQuery = "galaxy",
        initialImageResultsState = ImageResultsState()
    )

    fun consumeIntent(intent: ImageResultsIntent) {
        reduce(intent)
        when (intent) {
            is ImageResultsIntent.Search -> {
                viewModelScope.launch {
                    reduce(astroInteractor.produceImageResultsIntent(intent.query))
                }
            }
        }
    }

    private fun reduce(intent: ImageResultsIntent) = state.reduce(intent)
}
