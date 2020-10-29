package com.goofy.goober.state

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class AstroUi(initialState: AstroState) {
    val state: StateFlow<AstroState> get() = _state

    private val _state: MutableStateFlow<AstroState> = MutableStateFlow(initialState)

    fun reduce(intent: AstroIntent) {
         _state.value = _state.value.reduce(intent)
    }
}
