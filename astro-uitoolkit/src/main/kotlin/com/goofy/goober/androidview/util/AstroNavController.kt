package com.goofy.goober.androidview.util

import androidx.navigation.NavController as AndroidNavController

class AstroNavController(
    private val androidNavController: AndroidNavController,
    private val onExit: () -> Unit
) {
    operator fun invoke(block: AndroidNavController.() -> Unit) = androidNavController.block()
    fun exit() = onExit()
}
