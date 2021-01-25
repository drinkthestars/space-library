package com.goofy.goober.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.compose.state.ImageResultsScreenState
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * This is logically the same as [ImageSearchViewModel] in the astro-uitoolkit package.
 * The only difference is that this [ImageResultsScreenState]
 * uses Compose's [mutableStateOf]
 */
private const val initialQuery = "galaxy"

internal class ImageSearchViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = ImageResultsScreenState(
        initialQuery = initialQuery,
        initialImageResultsState = ImageResultsState()
    )

    init {
        astroInteractor
            .produceImageSearchResultIntents()
            .onEach { consumeIntent(it) }
            .launchIn(viewModelScope)
            .also { consumeIntent(ImageResultsIntent.Search(initialQuery)) }
    }

    fun consumeIntent(intent: ImageResultsIntent) {
        reduce(intent)
        doTasks(intent)
    }

    private fun doTasks(intent: ImageResultsIntent) {
        when (intent) {
            is ImageResultsIntent.Search -> astroInteractor.enqueueImageSearch(intent.query)
        }
    }

    private fun reduce(intent: ImageResultsIntent) = state.reduce(intent)
}
