package com.goofy.goober.ui.fragment

import androidx.fragment.app.Fragment

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Fragment.initArgs(fromParentFragment: Boolean = false): Lazy<T> {
    return lazy {
        if (fromParentFragment) {
            checkNotNull(requireParentFragment() as? FragmentArgsProvider<T>) {
                "Parent Fragment is not FragmentArgsProvider for ${T::class}"
            }
        } else {
            checkNotNull(requireActivity() as? FragmentArgsProvider<T>) {
                "Parent Activity is not a FragmentArgsProvider for ${T::class}"
            }
        }.provideFragmentArgs()
    }
}
