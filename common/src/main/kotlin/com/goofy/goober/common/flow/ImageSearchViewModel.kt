package com.goofy.goober.common.flow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.common.flow.model.ImageResultsScreenState
import com.goofy.goober.common.interactor.AstroInteractor
import com.goofy.goober.common.model.ImageResultsAction
import com.goofy.goober.common.model.ImageResultsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * This is logically the same as [ImageSearchViewModel] in the snapshot package.
 * The only difference is that this [ImageResultsScreenState]
 * uses [kotlinx.coroutines.flow.MutableStateFlow] instead of Compose's [mutableStateOf]
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
            .produceImageSearchResult()
            .onEach { dispatch(it) }
            .launchIn(viewModelScope)
            .also { dispatch(ImageResultsAction.Search(InitialQuery)) }
    }

    fun dispatch(action: ImageResultsAction) {
        updateState(action)
        middleware(action)
    }

    private fun middleware(action: ImageResultsAction) {
        when (action) {
            is ImageResultsAction.Search -> astroInteractor.enqueueImageSearch(action.query)
            ImageResultsAction.ShowError,
            is ImageResultsAction.ShowImages -> Unit
        }
    }

    private fun updateState(action: ImageResultsAction) = state.update(action)
}
