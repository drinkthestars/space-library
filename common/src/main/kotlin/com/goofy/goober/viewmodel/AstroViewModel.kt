package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.AstroInteractor
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.AstroUi
import com.goofy.goober.state.Splash
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AstroViewModel : ViewModel() {

    private val astroUi = AstroUi(initialState = Splash)
    val state: StateFlow<AstroState> get() = astroUi.state

    fun consumeIntent(intent: AstroIntent) = astroUi.reduce(intent)
}
