package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState
import com.goofy.goober.state.Splash
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class AstroViewModel(
    initState: AstroState = Splash(initialQuery = "galaxy")
) : ViewModel() {

    val state: StateFlow<AstroState> get() = _state

    private val _state: MutableStateFlow<AstroState> = MutableStateFlow(initState)

    fun consumeIntent(intent: AstroIntent) {
        _state.value = _state.value.reduce(intent)
    }
}
