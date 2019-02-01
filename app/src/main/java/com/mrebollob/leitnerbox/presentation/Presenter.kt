package com.mrebollob.leitnerbox.presentation

import com.mrebollob.leitnerbox.domain.exception.Failure


interface Presenter<V> {

    fun attachView(view: V)

    fun detachView() {}
}

interface View {
    fun handleFailure(failure: Failure)
}