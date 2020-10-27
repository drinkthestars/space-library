package com.goofy.goober.ui.state

import androidx.fragment.app.Fragment

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Fragment.bindState(fromParentFragment: Boolean = false): Lazy<T> {
    return lazy {
        if (fromParentFragment) {
            checkNotNull(requireParentFragment() as? FragmentStateProvider<T>) {
                "Parent Fragment is not FragmentStateProvider for ${T::class}"
            }
        } else {
            checkNotNull(requireActivity() as? FragmentStateProvider<T>) {
                "Parent Activity is not a FragmentStateProvider for ${T::class}"
            }
        }.provideFragmentStates()
    }
}
