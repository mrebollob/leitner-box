package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getLevelsCount
import com.mrebollob.leitnerbox.domain.usecase.getNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.getStartDate
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.domain.usecase.saveLevelsCount
import com.mrebollob.leitnerbox.domain.usecase.saveNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.saveStartDate
import com.mrebollob.leitnerbox.domain.usecase.saveStudyTime
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
    private var studyTime = Hour(0, 0)
    private var levelsCount = -1

    override fun attachView(view: SettingsView) {
        this.view = view

        GlobalScope.launch(context = executor.main) {

            levelsCount = getLevelsCount(repository)
            view.showLevelsCount(levelsCount)

            startDate = getStartDate(repository)
            view.showStartDate(startDate)

            studyTime = getStudyTime(repository)
            view.showStudyTime(studyTime)

            val isEnable = getNotificationEnable(repository)
            view.showNotificationEnable(isEnable)
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
        view?.showTimeSelector(studyTime)
    }

    fun onSetStartDate(startDate: Date) {
        this.startDate = startDate
        view?.showStartDate(startDate)
        GlobalScope.launch(context = executor.main) {
            saveStartDate(repository, startDate)
        }
    }

    fun onSetStudyTime(hour: Hour) {
        this.studyTime = hour
        view?.showStudyTime(hour)
        GlobalScope.launch(context = executor.main) {
            saveStudyTime(repository, hour)
        }
    }

    fun onNotificationEnableClick(isEnable: Boolean) {
        view?.showNotificationEnable(isEnable)
        GlobalScope.launch(context = executor.main) {
            saveNotificationEnable(repository, isEnable)
        }
    }
}

interface SettingsView {
    fun showStartDate(startDate: Date)
    fun showStudyTime(hour: Hour)
    fun showLevelsCount(levelsCount: Int)
    fun showLevelsCountSelector(levelsCount: Int)
    fun showDateSelector(startDate: Date)
    fun showTimeSelector(hour: Hour)
    fun showNotificationEnable(isEnable: Boolean)
}