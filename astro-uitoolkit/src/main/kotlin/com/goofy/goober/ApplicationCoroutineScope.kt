package com.goofy.goober

import kotlinx.coroutines.CoroutineScope

class ApplicationCoroutineScope(
    global: CoroutineScope
) : CoroutineScope by global
