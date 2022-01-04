package com.goofy.goober.common.flow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.common.interactor.AstroInteractor
import com.goofy.goober.common.model.DetailsAction
import com.goofy.goober.common.model.DetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * This is logically the same as [DetailsViewModel] in the snapshot package.
 * The only difference is that this
 * uses [kotlinx.coroutines.flow.MutableStateFlow] instead of Compose's [mutableStateOf]
 */
class DetailsViewModel(private val astroInteractor: AstroInteractor) : ViewModel() {

    var state = MutableStateFlow(DetailsState())

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
        state.value = state.value.reduce(action)
    }
}
