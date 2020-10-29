package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ImageSearchViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = MutableStateFlow(ImageResultsState(isLoading = true))

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

    private fun reduce(intent: ImageResultsIntent) {
        state.value = state.value.reduce(intent)
    }
}
