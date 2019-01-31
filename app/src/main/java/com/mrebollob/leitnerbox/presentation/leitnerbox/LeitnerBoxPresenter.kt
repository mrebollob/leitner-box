package com.mrebollob.leitnerbox.presentation.leitnerbox

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.interactor.GetCurrentDay
import com.mrebollob.leitnerbox.domain.interactor.GetDayLevels
import com.mrebollob.leitnerbox.domain.interactor.SaveDayCompleted
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.Presenter
import com.mrebollob.leitnerbox.presentation.View

class LeitnerBoxPresenter(
    private val getCurrentDay: GetCurrentDay,
    private val saveDayCompleted: SaveDayCompleted,
    private val getDayLevels: GetDayLevels
) : Presenter<LeitnerBoxView> {

    private var view: LeitnerBoxView? = null
    private var currentDay = LeitnerDay(0)

    override fun attachView(view: LeitnerBoxView) {
        this.view = view

        loadCurrentDay()
    }

    private fun loadCurrentDay() =
        getCurrentDay(UseCase.None()) {
            it.either(
                ::handleFailure,
                ::handleCurrentDay
            )
        }

    private fun handleCurrentDay(day: LeitnerDay) {

        this.currentDay = day

        if (day.dayNumber == 0) {
            view?.showFirstDayTitle()

        } else {
            view?.showCurrentNumberDay(day.dayNumber)
            getDayLevels(GetDayLevels.Params(day)) {
                it.either(
                    ::handleFailure,
                    ::handleDayLevels
                )
            }
        }
    }

    private fun handleDayLevels(levels: List<Level>) {
        view?.showLevels(levels)
        view?.showLevelsToReview(levels)
    }

    fun onDayCompletedClick() {
        saveDayCompleted(SaveDayCompleted.Params(currentDay)) {
            it.either(
                ::handleFailure,
                ::handleDayCompleted
            )
        }
    }

    private fun handleDayCompleted(day: LeitnerDay) {
        view?.onDayCompleted()
    }

    private fun handleFailure(failure: Failure) {
        view?.handleFailure(failure)
    }
}

interface LeitnerBoxView : View {
    fun showFirstDayTitle()
    fun showCurrentNumberDay(currentDay: Int)
    fun showLevelsToReview(levels: List<Level>)
    fun showLevels(levels: List<Level>)
    fun onDayCompleted()
}