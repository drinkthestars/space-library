package com.goofy.goober

import kotlinx.coroutines.CoroutineScope

class ApplicationCoroutineScope(globalScope: CoroutineScope) : CoroutineScope by globalScope
