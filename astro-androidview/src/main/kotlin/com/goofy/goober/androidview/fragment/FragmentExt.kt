package com.goofy.goober.androidview.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.goofy.goober.androidview.navigation.AstroNavArgsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.getViewModel
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
    getKoin().getViewModel<VM>(
        qualifier = qualifier,
        parameters = parameters,
        owner = { ViewModelOwner(findNavController().getBackStackEntry(fragmentId).viewModelStore) }
    )
}

internal inline fun <T> Fragment.collectWhenStarted(
    flow: Flow<T>,
    crossinline action: suspend (value: T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collect { action(it) }
    }
}
