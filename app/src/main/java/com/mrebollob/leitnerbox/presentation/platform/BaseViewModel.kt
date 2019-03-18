package com.mrebollob.leitnerbox.presentation.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrebollob.leitnerbox.domain.exception.Failure

abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}