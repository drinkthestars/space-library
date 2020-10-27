package com.goofy.goober.ui.state

interface FragmentStateProvider<T : Any> {
    fun provideFragmentStates(): T
}
