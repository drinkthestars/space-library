package com.goofy.goober.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.ViewModel
import com.goofy.goober.model.AstroNavAction
import com.goofy.goober.model.AstroNavState
import com.goofy.goober.model.Splash

class AstroNavViewModel(
    initNavState: AstroNavState = Splash
) : ViewModel() {

    var state by mutableStateOf<AstroNavState>(initNavState)
        private set

    fun dispatch(navAction: AstroNavAction) {
        withMutableSnapshot {
            state = state.reduce(navAction)
        }
    }
}
