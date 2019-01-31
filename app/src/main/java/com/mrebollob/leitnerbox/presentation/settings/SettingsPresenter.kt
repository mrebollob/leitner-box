package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.interactor.GetNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.GetStudyTime
import com.mrebollob.leitnerbox.domain.interactor.SaveNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.SaveStudyTime
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.presentation.Presenter
import com.mrebollob.leitnerbox.presentation.View

class SettingsPresenter(
    private val getStudyTime: GetStudyTime,
    private val saveStudyTime: SaveStudyTime,
    private val getNotificationEnabled: GetNotificationEnable,
    private val saveNotificationEnabled: SaveNotificationEnable
) : Presenter<SettingsView> {

    private var view: SettingsView? = null
    private var studyTime = Hour(0, 0)

    override fun attachView(view: SettingsView) {
        this.view = view

        loadStudyTime()
        loadNotificationEnabled()
    }

    private fun loadStudyTime() =
        getStudyTime(UseCase.None()) {
            it.either(
                ::handleFailure,
                ::handleStudyTime
            )
        }

    private fun loadNotificationEnabled() =
        getNotificationEnabled(UseCase.None()) {
            it.either(
                ::handleFailure,
                ::handleNotificationEnabled
            )
        }

    private fun handleStudyTime(studyHour: Hour) {

        this.studyTime = studyHour
        view?.showStudyTime(studyTime)
    }

    private fun handleNotificationEnabled(isEnable: Boolean) {
        view?.showNotificationEnable(isEnable)
    }

    fun onSetNotificationHourClick() {
        view?.showTimeSelector(studyTime)
    }

    fun onSetStudyTime(hour: Hour) =
        saveStudyTime(SaveStudyTime.Params(hour)) {
            it.either(
                ::handleFailure,
                ::handleStudyTime
            )
        }

    fun onNotificationEnableClick(isEnabled: Boolean) =
        saveNotificationEnabled(SaveNotificationEnable.Params(isEnabled)) {
            it.either(
                ::handleFailure,
                ::handleNotificationEnabled
            )
        }

    private fun handleFailure(failure: Failure) {
        view?.handleFailure(failure)
    }
}

interface SettingsView : View {
    fun showStudyTime(hour: Hour)
    fun showTimeSelector(hour: Hour)
    fun showNotificationEnable(isEnable: Boolean)
}