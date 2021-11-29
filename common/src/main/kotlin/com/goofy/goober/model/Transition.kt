package com.goofy.goober.model

data class Transition(
    val fromState: AstroState,
    val toState: AstroState,
    val intent: AstroAction
)
