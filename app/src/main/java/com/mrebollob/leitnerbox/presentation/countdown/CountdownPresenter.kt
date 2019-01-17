package com.mrebollob.leitnerbox.presentation.countdown

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.VOID_HOUR
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.domain.usecase.isTodayCompleted
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

const val STUDY_HOUR_THRESHOLD = 6

class CountdownPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<CountdownView> {

    private var view: CountdownView? = null
    private var studyHour = VOID_HOUR
    private var isTodayCompleted = false

    override fun attachView(view: CountdownView) {
        this.view = view

        loadDate()
    }

    override fun detachView() {

    }

    private fun loadDate() = GlobalScope.launch(context = executor.main) {

        studyHour = getStudyTime(repository)
        isTodayCompleted = isTodayCompleted(repository, Date())

        view?.showStudyTimeCountdown(
            studyHour,
            isTodayCompleted(repository, Date())
        )

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
        if (studyHour == VOID_HOUR || isTodayCompleted) {
            false
        } else {
            studyHour.getHoursUntilDate(Date()) < STUDY_HOUR_THRESHOLD
        }
}

interface CountdownView {
    fun showStudyTimeCountdown(studyTime: Hour, addDay: Boolean)
    fun showAdvanceTimeError()
    fun goToLeitnerBoxScreen()
    fun showLeitnerButtonEnabled()
    fun showLeitnerButtonDisabled()
}