package com.mrebollob.leitnerbox.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface Executor {
    val main: CoroutineDispatcher
}