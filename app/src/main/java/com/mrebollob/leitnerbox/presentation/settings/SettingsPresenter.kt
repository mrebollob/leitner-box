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

    fun onSetLevelsClick() {
        view?.showLevelsCountSelector(levelsCount)
    }

    fun onSetLevelsCount(levelsCount: Int) {
        this.levelsCount = levelsCount
        view?.showLevelsCount(levelsCount)
        GlobalScope.launch(context = executor.main) {
            saveLevelsCount(repository, levelsCount)
        }
    }

    fun onSetStartDateClick() {
        view?.showDateSelector(startDate)
    }

    fun onSetNotificationHourClick() {
        view?.showTimeSelector(1, 54)
    }

    fun onSetStartDate(startDate: Date) {
        this.startDate = startDate
        view?.showStartDate(startDate)
        GlobalScope.launch(context = executor.main) {
            saveStartDate(repository, startDate)
        }
    }

    fun onSetStudyTime(hour: Int, minute: Int) {

        view?.showStudyTime(hour, minute)
    }
}

interface SettingsView {
    fun showStartDate(startDate: Date)
    fun showStudyTime(hour: Int, minute: Int)
    fun showLevelsCount(levelsCount: Int)
    fun showLevelsCountSelector(levelsCount: Int)
    fun showDateSelector(startDate: Date)
    fun showTimeSelector(hour: Int, minute: Int)
}