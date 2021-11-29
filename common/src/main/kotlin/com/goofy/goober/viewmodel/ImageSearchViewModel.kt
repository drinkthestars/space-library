package com.goofy.goober.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.ImageResultsAction
import com.goofy.goober.model.ImageResultsScreenState
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * This is logically the same as [ImageSearchViewModel] in the astro-uitoolkit package.
 * The only difference is that this [ImageResultsScreenState]
 * uses Compose's [mutableStateOf]
 */
private const val InitialQuery = "galaxy"

class ImageSearchViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = ImageResultsScreenState(
        initialQuery = InitialQuery,
        initialImageResultsState = ImageResultsState()
    )

    init {
        astroInteractor
            .produceImageSearchResultIntents()
            .onEach { dispatch(it) }
            .launchIn(viewModelScope)
            .also { dispatch(ImageResultsAction.Search(InitialQuery)) }
    }

    fun dispatch(action: ImageResultsAction) {
        updateState(action)
        middleware(action)
    }

    private fun middleware(intent: ImageResultsAction) {
        when (intent) {
            is ImageResultsAction.Search -> astroInteractor.enqueueImageSearch(intent.query)
            ImageResultsAction.ShowError,
            is ImageResultsAction.ShowImages -> Unit
        }
    }

    private fun updateState(action: ImageResultsAction) = state.update(action)
}
