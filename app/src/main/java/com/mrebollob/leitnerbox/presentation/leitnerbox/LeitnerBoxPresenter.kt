package com.mrebollob.leitnerbox.presentation.leitnerbox

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getCurrentDay
import com.mrebollob.leitnerbox.domain.usecase.getLevels
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LeitnerBoxPresenter(
    private val executor: Executor,
    private val repository: Repository,
    private val leitnerBox: LeitnerBox
) : Presenter<LeitnerBoxView> {

    private var view: LeitnerBoxView? = null

    override fun attachView(view: LeitnerBoxView) {
        this.view = view

        loadLevels()
        loadCurrentDay()
    }

    override fun detachView() {

    }

    fun onDoneClick() {
        view?.showDayAsDone()
    }

    private fun loadLevels() {
        GlobalScope.launch(context = executor.main) {
            val levels = getLevels(repository, leitnerBox, Date())
            view?.showLevels(levels)
        }
    }

    private fun loadCurrentDay() {
        GlobalScope.launch(context = executor.main) {
            view?.showCurrentNumberDay(getCurrentDay(repository, Date()))
        }
    }
}

interface LeitnerBoxView {
    fun showCurrentNumberDay(currentDay: Int)
    fun showLevels(levels: List<Level>)
    fun showDayAsDone()
}