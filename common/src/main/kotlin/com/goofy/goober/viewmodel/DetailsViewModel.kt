package com.goofy.goober.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.model.DetailsState
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
        withMutableSnapshot {
            state = state.reduce(action)
        }
    }
}
