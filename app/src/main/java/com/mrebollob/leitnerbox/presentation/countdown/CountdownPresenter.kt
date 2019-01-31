package com.mrebollob.leitnerbox.presentation.countdown

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.interactor.CheckDayDayCompleted
import com.mrebollob.leitnerbox.domain.interactor.GetStudyTime
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.presentation.Presenter
import com.mrebollob.leitnerbox.presentation.View
import java.util.*

const val STUDY_HOUR_THRESHOLD = 6

class CountdownPresenter(
    private val getStudyTime: GetStudyTime,
    private val checkDayDayCompleted: CheckDayDayCompleted
) : Presenter<CountdownView> {

    private var view: CountdownView? = null
    private var studyHour = Hour.empty()
    private var isTodayCompleted = false

    override fun attachView(view: CountdownView) {
        this.view = view

        loadData()
    }

    private fun loadData() =
        checkDayDayCompleted(CheckDayDayCompleted.Params(Date())) {
            it.either(
                ::handleFailure,
                ::loadStudyTime
            )
        }

    private fun loadStudyTime(isTodayCompleted: Boolean) =
        getStudyTime(UseCase.None()) {
            it.either(
                ::handleFailure
            ) { handleStudyTime(it, isTodayCompleted) }
        }

    private fun handleStudyTime(studyHour: Hour, isTodayCompleted: Boolean) {

        this.studyHour = studyHour
        this.isTodayCompleted = isTodayCompleted

        view?.showStudyTimeCountdown(studyHour, isTodayCompleted)

        if (isLeitnerButtonEnabled()) {
            view?.showLeitnerButtonEnabled()
        } else {
            view?.showLeitnerButtonDisabled()
        }
    }

    fun onShowLeitnerClick() {
        if (isLeitnerButtonEnabled()) {
            view?.goToLeitnerBoxScreen()
        } else {
            view?.showAdvanceTimeError()
        }
    }

    private fun isLeitnerButtonEnabled(): Boolean =
        if (studyHour == Hour.empty() || isTodayCompleted) {
            false
        } else {
            studyHour.getHoursUntilDate(Date()) < STUDY_HOUR_THRESHOLD
        }

    private fun handleFailure(failure: Failure) {
        view?.handleFailure(failure)
    }
}

interface CountdownView : View {
    fun showStudyTimeCountdown(studyTime: Hour, addDay: Boolean)
    fun showAdvanceTimeError()
    fun goToLeitnerBoxScreen()
    fun showLeitnerButtonEnabled()
    fun showLeitnerButtonDisabled()
}