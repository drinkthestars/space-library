package com.goofy.goober.common.snapshot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.common.interactor.AstroInteractor
import com.goofy.goober.common.model.DetailsAction
import com.goofy.goober.common.model.DetailsState
import kotlinx.coroutines.launch

class DetailsViewModel(private val astroInteractor: AstroInteractor) : ViewModel() {

    var state by mutableStateOf(DetailsState())

    fun dispatch(action: DetailsAction) {
        reduce(action)
        when (action) {
            is DetailsAction.LoadContent -> {
                viewModelScope.launch {
                    reduce(astroInteractor.produceDisplayDetails(action.image))
                }
            }
            is DetailsAction.DisplayContent,
            DetailsAction.ShowError -> Unit
        }
    }

    private fun reduce(action: DetailsAction) {
        state = state.reduce(action)
    }
}
