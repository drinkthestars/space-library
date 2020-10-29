package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.model.DetailsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = MutableStateFlow(DetailsState(isLoading = true))

    fun consumeIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.DisplayContent -> {
                viewModelScope.launch {
                    reduce(astroInteractor.produceDisplayDetailsIntent())
                }
            }
        }
    }

    private fun reduce(intent: DetailsIntent) {
        state.value = state.value.reduce(intent)
    }
}
