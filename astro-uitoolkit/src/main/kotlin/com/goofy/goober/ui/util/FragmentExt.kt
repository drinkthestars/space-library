package com.goofy.goober.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.goofy.goober.ui.navigation.AstroNavArgsViewModel

inline fun <reified T : Any> Fragment.activityArgs(): Lazy<T> {
    return lazy {
        val navArgsViewModel by activityViewModels<AstroNavArgsViewModel>()
        checkNotNull(navArgsViewModel as? T) {
            "AstroNavArgsViewModel not implemented with ${T::class}"
        }
    }
}
