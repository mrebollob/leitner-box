package com.mrebollob.leitnerbox.presentation


interface Presenter<V> {

    fun attachView(view: V)

    fun detachView()
}