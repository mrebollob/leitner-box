package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.domain.usecase.saveNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.saveStudyTime
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<SettingsView> {

    private var view: SettingsView? = null
    private var studyTime = Hour(0, 0)

    override fun attachView(view: SettingsView) {
        this.view = view

        loadStudyData()
    }

    override fun detachView() {

    }

    private fun loadStudyData() = GlobalScope.launch(context = executor.main) {
        studyTime = getStudyTime(repository)
        view?.showStudyTime(studyTime)

        val isEnable = getNotificationEnable(repository)
        view?.showNotificationEnable(isEnable)
    }

    fun onSetStudyTime(hour: Hour) {
        this.studyTime = hour
        view?.showStudyTime(hour)
        GlobalScope.launch(context = executor.main) {
            saveStudyTime(repository, hour)
        }
    }

    fun onSetNotificationHourClick() {
        view?.showTimeSelector(studyTime)
    }

    fun onNotificationEnableClick(isEnable: Boolean) {
        view?.showNotificationEnable(isEnable)
        GlobalScope.launch(context = executor.main) {
            saveNotificationEnable(repository, isEnable)
        }
    }
}

interface SettingsView {
    fun showStudyTime(hour: Hour)
    fun showTimeSelector(hour: Hour)
    fun showNotificationEnable(isEnable: Boolean)
}