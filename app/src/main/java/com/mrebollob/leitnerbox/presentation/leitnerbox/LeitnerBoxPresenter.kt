package com.mrebollob.leitnerbox.presentation.leitnerbox

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getLastDayCompleted
import com.mrebollob.leitnerbox.domain.usecase.getLevels
import com.mrebollob.leitnerbox.domain.usecase.isDayCompleted
import com.mrebollob.leitnerbox.domain.usecase.saveLastDayCompleted
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeitnerBoxPresenter(
    private val executor: Executor,
    private val repository: Repository,
    private val leitnerBox: LeitnerBox
) : Presenter<LeitnerBoxView> {

    private var view: LeitnerBoxView? = null
    private var currentDay = LeitnerDay(0)

    override fun attachView(view: LeitnerBoxView) {
        this.view = view

        loadLevels()
    }

    override fun detachView() {

    }

    fun onDayCompletedClick() = GlobalScope.launch(context = executor.main) {
        saveLastDayCompleted(repository, currentDay)
        view?.onDayCompleted()
    }

    private fun updateDoneView() = GlobalScope.launch(context = executor.main) {
        if (isDayCompleted(repository, currentDay)) {
            view?.showDayDone()
        } else {
            view?.showDayToDo()
        }
    }

    private fun loadLevels() = GlobalScope.launch(context = executor.main) {

        val lastDayCompleted = getLastDayCompleted(repository)
        currentDay = LeitnerDay(lastDayCompleted.number + 1)

        view?.showCurrentNumberDay(currentDay.number)
        val levels = getLevels(leitnerBox, currentDay.number)
        view?.showLevels(levels)

        updateDoneView()
    }
}

interface LeitnerBoxView {
    fun showCurrentNumberDay(currentDay: Int)
    fun showLevels(levels: List<Level>)
    fun showDayDone()
    fun showDayToDo()
    fun onDayCompleted()
}