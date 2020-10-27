package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.model.AstroIntent
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.AstroUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class AstroViewModel(
    astroInteractor: AstroInteractor,
    private val astroUi: AstroUi,
) : ViewModel() {

    val state: StateFlow<AstroState> get() = astroUi.state

    init {
        viewModelScope.launch {
            astroUi.reduce(astroInteractor.produceInitIntent())
        }
    }

    fun consumeIntent(intent: AstroIntent) = astroUi.reduce(intent)
}
