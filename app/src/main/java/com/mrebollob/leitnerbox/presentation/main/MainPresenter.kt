package com.mrebollob.leitnerbox.presentation.main


import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.interactor.GetNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.presentation.Presenter
import com.mrebollob.leitnerbox.presentation.View
import com.mrebollob.leitnerbox.presentation.splash.FirstStartHandler

@Deprecated("Use view model")
class MainPresenter(
//    private val getStudyTime: GetStudyTime,
    private val getNotificationEnabled: GetNotificationEnable,
    private val firstStartHandler: FirstStartHandler
) : Presenter<MainView> {

    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view

        handleFirstStart()
    }

    private fun handleFirstStart() {
        if (firstStartHandler.isFirstStart()) {
            firstStartHandler.saveFirstStart()
            view?.goToIntroScreen()
        } else {
            view?.showCountdownView(false)
        }
    }

    fun refreshConfig() =
        getNotificationEnabled(UseCase.None()) {
            it.either(
                ::handleFailure,
                ::handleNotificationEnabled
            )
        }

    private fun handleNotificationEnabled(isEnable: Boolean) {
        if (isEnable) {
//            loadStudyTime()
        } else {
            view?.cancelNextNotification()
        }
    }

    private fun handleStudyTime(studyHour: Hour) {
        view?.initNotification(studyHour)
    }


    fun onSettingsClick() {
        view?.goToSettingsScreen()
    }

    fun onAboutClick() {
        view?.goToAboutScreen()
    }

    fun onShowLeitnerBoxClick() {
        view?.showLeitnerView()
    }

    fun onDayCompleted() {
        view?.showCountdownView(true)
    }

    private fun handleFailure(failure: Failure) {
        view?.handleFailure(failure)
    }
}

interface MainView : View {
    fun showLeitnerView()
    fun showCountdownView(withAnimation: Boolean)
    fun goToIntroScreen()
    fun goToSettingsScreen()
    fun goToAboutScreen()
    fun initNotification(studyHour: Hour)
    fun cancelNextNotification()
}