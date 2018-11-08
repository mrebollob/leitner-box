package com.mrebollob.leitnerbox.util.executor


import com.mrebollob.leitnerbox.domain.executor.Executor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AndroidExecutor : Executor {
    override val main: CoroutineDispatcher = Dispatchers.Main
}