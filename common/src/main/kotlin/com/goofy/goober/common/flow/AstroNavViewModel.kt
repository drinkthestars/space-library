package com.goofy.goober.common.flow

import androidx.lifecycle.ViewModel
import com.goofy.goober.common.model.AstroNavAction
import com.goofy.goober.common.model.AstroNavState
import com.goofy.goober.common.model.Splash
import kotlinx.coroutines.flow.MutableStateFlow

class AstroNavViewModel(initNavState: AstroNavState = Splash) : ViewModel() {

    val state = MutableStateFlow(initNavState)

    fun dispatch(navAction: AstroNavAction) {
        state.value = state.value.reduce(navAction)
    }
}
