package com.goofy.goober.androidview.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

inline fun <reified T : Any> Fragment.activityArgs(): Lazy<T> {
    return lazy {
        val navArgsViewModel by activityViewModels<AstroNavArgsViewModel>()
        checkNotNull(navArgsViewModel as? T) {
            "AstroNavArgsViewModel not implemented with ${T::class}"
        }
    }
}
