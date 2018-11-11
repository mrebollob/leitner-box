package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getLevelsCount
import com.mrebollob.leitnerbox.domain.usecase.getStartDate
import com.mrebollob.leitnerbox.domain.usecase.saveLevelsCount
import com.mrebollob.leitnerbox.domain.usecase.saveStartDate
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class SettingsPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<SettingsView> {

    private var view: SettingsView? = null
    private var startDate = Date()
    private var levelsCount = -1

    override fun attachView(view: SettingsView) {
        this.view = view

        GlobalScope.launch(context = executor.main) {
            levelsCount = getLevelsCount(repository)
            view.showLevelsCount(levelsCount)

            startDate = getStartDate(repository)
            view.showStartDate(startDate)
        }
    }

    override fun detachView() {

    }

    fun onSettingsLevelsClick() {
        view?.showLevelsCountSelector(levelsCount)
    }

    fun onSetLevelsCount(levelsCount: Int) {
        this.levelsCount = levelsCount
        view?.showLevelsCount(levelsCount)
        GlobalScope.launch(context = executor.main) {
            saveLevelsCount(repository, levelsCount)
        }
    }

    fun onSettingsStartDateClick() {
        view?.showDateSelector(startDate)
    }

    fun onSetStartDate(startDate: Date) {
        this.startDate = startDate
        view?.showStartDate(startDate)
        GlobalScope.launch(context = executor.main) {
            saveStartDate(repository, startDate)
        }
    }
}

interface SettingsView {
    fun showStartDate(startDate: Date)
    fun showLevelsCount(levelsCount: Int)
    fun showLevelsCountSelector(levelsCount: Int)
    fun showDateSelector(startDate: Date)
}