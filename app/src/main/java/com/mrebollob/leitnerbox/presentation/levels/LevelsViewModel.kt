package com.mrebollob.leitnerbox.presentation.levels

import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetDayLevels
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import javax.inject.Inject

class LevelsViewModel @Inject constructor(
    private val getDayLevels: GetDayLevels
) : BaseViewModel() {

    var levels: MutableLiveData<Level> = MutableLiveData()

    fun setDay(day: Int) {
        getDayLevels(GetDayLevels.Params(day)) {
            it.either(::handleFailure, ::handleLevels)
        }
    }

    private fun handleLevels(levels: Level) {
        this.levels.value = levels
    }
}