package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {

        uiScope.launch {
            val deferred = async(Dispatchers.Default) { run(params) }
            onResult(deferred.await())
        }
    }

    class None
}
