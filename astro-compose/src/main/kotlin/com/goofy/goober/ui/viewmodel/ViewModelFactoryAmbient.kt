package com.goofy.goober.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.staticAmbientOf
import com.goofy.goober.viewmodel.ViewModelFactory

val ViewModelFactoryAmbient = staticAmbientOf<ViewModelFactory> {
    error("No ViewModelProvider.Factory provided!")
}

@Composable
fun ProvideViewModelFactory(
    viewModelFactory: ViewModelFactory,
    content: @Composable () -> Unit
) {
    Providers(ViewModelFactoryAmbient provides viewModelFactory) {
        content()
    }
}
