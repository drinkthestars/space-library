package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.model.DetailsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModel(
    private val astroInteractor: AstroInteractor
) : ViewModel() {

    val state = MutableStateFlow(DetailsState())

    fun dispatch(action: DetailsAction) {
        reduce(action)
        when (action) {
            is DetailsAction.LoadContent -> {
                viewModelScope.launch {
                    reduce(astroInteractor.produceDisplayDetailsIntent(action.image))
                }
            }
        }
    }

    private fun reduce(action: DetailsAction) {
        state.value = state.value.reduce(action)
    }
}
