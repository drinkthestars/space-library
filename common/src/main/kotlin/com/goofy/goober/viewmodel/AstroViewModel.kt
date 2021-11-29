package com.goofy.goober.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.ViewModel
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.AstroState
import com.goofy.goober.model.Splash

class AstroViewModel(
    initState: AstroState = Splash
) : ViewModel() {

    var state by mutableStateOf<AstroState>(initState)
        private set

    fun dispatch(action: AstroAction) {
        withMutableSnapshot {
            state = state.reduce(action)
        }
    }
}
