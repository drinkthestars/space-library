package com.goofy.goober.common.snapshot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.goofy.goober.common.model.AstroNavAction
import com.goofy.goober.common.model.AstroNavState
import com.goofy.goober.common.model.Splash

class AstroNavViewModel(
    initNavState: AstroNavState = Splash
) : ViewModel() {

    var state by mutableStateOf<AstroNavState>(initNavState)
        private set

    fun dispatch(navAction: AstroNavAction) {
        state = state.reduce(navAction)
    }
}
