package com.mrebollob.leitnerbox.presentation.main.countdown

import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetNextStudyTime
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import java.util.*
import javax.inject.Inject

class CountdownViewModel @Inject constructor(
    private val getNextStudyTime: GetNextStudyTime
) : BaseViewModel() {

    var studyTime: MutableLiveData<Date> = MutableLiveData()

    fun init() {

        getNextStudyTime(UseCase.None()) {
            it.either(::handleFailure, ::handleStudyTime)
        }
    }

    private fun handleStudyTime(studyTime: Date) {
        this.studyTime.value = studyTime
    }
}