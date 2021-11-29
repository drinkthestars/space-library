package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.Splash
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class AstroViewModel(
    initState: AstroState = Splash
) : ViewModel() {

    val state: StateFlow<AstroState> get() = _state

    private val _state: MutableStateFlow<AstroState> = MutableStateFlow(initState)

    fun consumeIntent(intent: AstroAction) {
        _state.value = _state.value.reduce(intent)
    }
}
