package com.goofy.goober.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class AstroUi {
    val state: StateFlow<AstroState> get() = _state

    private val _state: MutableStateFlow<AstroState> = MutableStateFlow(SplashState)

    operator fun invoke(block: AstroUi.() -> Unit) = block()

    fun reduce(intent: AstroIntent) {
        val fromState = _state.value
        val toState = fromState.reduce(intent)
        if (fromState != toState) _state.value = toState
    }
}
