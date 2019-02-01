package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.interactor.GetCurrentDay
import com.mrebollob.leitnerbox.domain.interactor.GetNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.GetStudyTime
import com.mrebollob.leitnerbox.domain.interactor.SaveCurrentDay
import com.mrebollob.leitnerbox.domain.interactor.SaveNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.SaveStudyTime
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.presentation.Presenter
import com.mrebollob.leitnerbox.presentation.View

class SettingsPresenter(
    private val getCurrentDay: GetCurrentDay,
    private val saveCurrentDay: SaveCurrentDay,
    private val getStudyTime: GetStudyTime,
    private val saveStudyTime: SaveStudyTime,
    private val getNotificationEnabled: GetNotificationEnable,
    private val saveNotificationEnabled: SaveNotificationEnable
) : Presenter<SettingsView> {

    private var view: SettingsView? = null
    private var currentDay = LeitnerDay.empty()
    private var studyTime = Hour.empty()

    override fun attachView(view: SettingsView) {
        this.view = view

        loadCurrentDay()
        loadStudyTime()
        loadNotificationEnabled()
    }

    private fun loadCurrentDay() =
        getCurrentDay(UseCase.None()) {
            it.either(
                ::handleFailure,
                ::handleCurrentDay
            )
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

    private fun handleCurrentDay(day: LeitnerDay) {
        this.currentDay = day
        view?.showCurrentDay(day)
    }

    private fun handleStudyTime(studyHour: Hour) {

        this.studyTime = studyHour
        view?.showStudyTime(studyTime)
    }

    private fun handleNotificationEnabled(isEnable: Boolean) {
        view?.showNotificationEnable(isEnable)
    }

    fun onSetCurrentDayClick() {
        view?.showDaySelector(currentDay)
    }

    fun onSetNotificationHourClick() {
        view?.showTimeSelector(studyTime)
    }

    fun onSetCurrentDay(day: LeitnerDay) =
        saveCurrentDay(SaveCurrentDay.Params(day)) {
            it.either(
                ::handleFailure,
                ::handleCurrentDay
            )
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
    fun showCurrentDay(day: LeitnerDay)
    fun showStudyTime(hour: Hour)
    fun showTimeSelector(hour: Hour)
    fun showDaySelector(day: LeitnerDay)
    fun showNotificationEnable(isEnable: Boolean)
}