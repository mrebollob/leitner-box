package com.mrebollob.leitnerbox.presentation.levels

import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetHomework
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import javax.inject.Inject

class LevelsViewModel @Inject constructor(
    private val getHomework: GetHomework
) : BaseViewModel() {

    var homework: MutableLiveData<Homework> = MutableLiveData()

    fun setDay(day: Int) {
        getHomework(GetHomework.Params(day)) {
            it.either(::handleFailure, ::handleHomework)
        }
    }

    private fun handleHomework(homework: Homework) {
        this.homework.value = homework
    }
}