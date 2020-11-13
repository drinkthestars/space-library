package com.goofy.goober.compose.viewmodel

/**
 * Can't use until Kotlin 1.4.20 + Compose support
 * because of https://youtrack.jetbrains.com/issue/KT-41006
 */


//val ViewModelFactoryAmbient = staticAmbientOf<ViewModelFactory> {
//    error("No ViewModelProvider.Factory provided!")
//}
//
//@Composable
//fun ProvideViewModelFactory(
//    viewModelFactory: ViewModelFactory,
//    content: @Composable () -> Unit
//) {
//    Providers(ViewModelFactoryAmbient provides viewModelFactory) {
//        content()
//    }
//}
