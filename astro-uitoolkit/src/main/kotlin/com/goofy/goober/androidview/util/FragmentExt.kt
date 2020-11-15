package com.goofy.goober.androidview.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ViewModelParameter
import org.koin.android.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> Fragment.activityArgs(): Lazy<T> {
    return lazy {
        val navArgsViewModel by activityViewModels<AstroNavArgsViewModel>()
        checkNotNull(navArgsViewModel as? T) {
            "AstroNavArgsViewModel not implemented with ${T::class}"
        }
    }
}

/**
 * Returns an instance of [VM] scoped to the given [fragmentId]'s
 * [androidx.navigation.NavBackStackEntry]
 */
inline fun <reified VM : ViewModel> Fragment.backStackEntryViewModel(
    @IdRes fragmentId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val store = findNavController().getBackStackEntry(fragmentId).viewModelStore
    getKoin().getViewModel(ViewModelParameter(VM::class, qualifier, parameters, store))
}


inline fun <reified VM : ViewModel> Fragment.sharedNavArgsViewModel(
    @IdRes navGraphId: Int = 0,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val store = findNavController().getViewModelStoreOwner(navGraphId).viewModelStore
    getKoin().getViewModel(ViewModelParameter(VM::class, qualifier, parameters, store))
}
