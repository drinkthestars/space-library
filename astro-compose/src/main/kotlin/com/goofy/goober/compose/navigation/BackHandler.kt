/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goofy.goober.compose.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticAmbientOf

val BackPressedDispatcherAmbient = staticAmbientOf<OnBackPressedDispatcherOwner> {
    error("No OnBackPressedDispatcherOwner provided!")
}

@Composable
fun backPressHandler(onBackPressed: () -> Unit, enabled: Boolean = true) {
    val dispatcher = BackPressedDispatcherAmbient.current.onBackPressedDispatcher

    val backCallback = remember(onBackPressed) {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    onCommit(enabled) {
        backCallback.isEnabled = enabled
    }

    onCommit(dispatcher, onBackPressed) {
        dispatcher.addCallback(backCallback)
        onDispose { backCallback.remove() }
    }
}
