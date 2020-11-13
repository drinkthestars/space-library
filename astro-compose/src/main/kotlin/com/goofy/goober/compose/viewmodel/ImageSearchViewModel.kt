package com.goofy.goober.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.compose.state.ImageResultsScreenState
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.launch

/**
 * This is logically the same as [ImageSearchViewModel] in the astro-uitoolkit package.
 * The only difference is that this [ImageResultsScreenState]
 * uses Compose's [mutableStateOf]
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
