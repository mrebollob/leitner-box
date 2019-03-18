package com.mrebollob.leitnerbox.presentation.main

import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetStudyTime
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getStudyTime: GetStudyTime
) : BaseViewModel() {

    var studyTime: MutableLiveData<Hour> = MutableLiveData()

    fun init() {

        getStudyTime(UseCase.None()) {
            it.either(::handleFailure, ::handleStudyTime)
        }
    }

    private fun handleStudyTime(studyTime: Hour) {
        this.studyTime.value = studyTime
    }
}