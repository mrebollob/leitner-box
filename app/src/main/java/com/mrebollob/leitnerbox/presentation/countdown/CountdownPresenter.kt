package com.mrebollob.leitnerbox.presentation.countdown

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountdownPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<CountdownView> {

    private var view: CountdownView? = null

    override fun attachView(view: CountdownView) {
        this.view = view

        GlobalScope.launch(context = executor.main) {

            val studyHour = getStudyTime(repository)
            view.showStudyTime(studyHour)
            view.showCountdown(studyHour)
        }
    }

    override fun detachView() {

    }

    fun onSkipButtonClick() {
        view?.goToLeitnerBoxScreen()
    }
}

interface CountdownView {
    fun showStudyTime(studyTime: Hour)
    fun showCountdown(studyTime: Hour)
    fun goToLeitnerBoxScreen()
}