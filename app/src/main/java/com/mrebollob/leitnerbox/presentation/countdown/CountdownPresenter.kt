package com.mrebollob.leitnerbox.presentation.countdown

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.domain.usecase.isTodayCompleted
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CountdownPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<CountdownView> {

    private var view: CountdownView? = null

    override fun attachView(view: CountdownView) {
        this.view = view

        loadDate()
    }

    override fun detachView() {

    }

    private fun loadDate() = GlobalScope.launch(context = executor.main) {

        val studyHour = getStudyTime(repository)
        view?.showStudyTimeCountdown(
            studyHour,
            isTodayCompleted(repository, Date())
        )
    }

    fun onShowLeitnerClick() {
        if (false) {
            view?.goToLeitnerBoxScreen()
        } else {
            view?.showAdvanceTimeError()
        }
    }
}

interface CountdownView {
    fun showStudyTimeCountdown(studyTime: Hour, addDay: Boolean)
    fun showAdvanceTimeError()
    fun goToLeitnerBoxScreen()
}