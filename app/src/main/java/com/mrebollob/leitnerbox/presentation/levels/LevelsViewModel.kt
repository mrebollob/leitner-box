package com.mrebollob.leitnerbox.presentation.levels

import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetCompletedDay
import com.mrebollob.leitnerbox.domain.interactor.GetHomework
import com.mrebollob.leitnerbox.domain.interactor.SaveCompletedDay
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import javax.inject.Inject

class LevelsViewModel @Inject constructor(
    private val getCompletedDay: GetCompletedDay,
    private val saveCompletedDay: SaveCompletedDay,
    private val getHomework: GetHomework
) : BaseViewModel() {

    var homework: MutableLiveData<Homework> = MutableLiveData()

    fun init() {
        getCompletedDay(UseCase.None()) {
            it.either(::handleFailure, ::handleLeitnerDay)
        }
    }

    fun setCompletedDay(day: LeitnerDay) {
        saveCompletedDay(SaveCompletedDay.Params(day)) {
            it.either(::handleFailure, ::handleCompletedDay)
        }
    }

    private fun handleCompletedDay(leitnerDay: LeitnerDay) {

    }

    private fun handleLeitnerDay(leitnerDay: LeitnerDay) {
        getHomework(GetHomework.Params(leitnerDay.dayNumber + 1)) {
            it.either(::handleFailure, ::handleHomework)
        }
    }

    private fun handleHomework(homework: Homework) {
        this.homework.value = homework
    }
}