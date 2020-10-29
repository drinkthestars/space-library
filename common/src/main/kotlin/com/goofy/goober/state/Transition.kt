package com.goofy.goober.state

import com.goofy.goober.state.AstroIntent
import com.goofy.goober.state.AstroState

data class Transition(
    val fromState: AstroState,
    val toState: AstroState,
    val intent: AstroIntent
)
