package com.goofy.goober.ui.fragment

interface FragmentArgsProvider<T : Any> {
    fun provideFragmentArgs(): T
}
