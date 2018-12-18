package com.mrebollob.leitnerbox.presentation.main


import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.domain.usecase.isFirstStart
import com.mrebollob.leitnerbox.domain.usecase.setFirstStart
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<MainView> {

    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view

        GlobalScope.launch(context = executor.main) {
            if (isFirstStart(repository)) {
                setFirstStart(repository, false)
                view.goToIntroScreen()
            } else {
                view.showCountdownView()
            }

            val isEnable = getNotificationEnable(repository)
            if (isEnable) {
                val studyHour = getStudyTime(repository)
                view.initNotification(studyHour)
            } else {
                view.cancelNextNotification()
            }
        }
    }

    override fun detachView() {

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
        view?.showCountdownView()
    }
}

interface MainView {
    fun showLeitnerView()
    fun showCountdownView()
    fun goToIntroScreen()
    fun goToSettingsScreen()
    fun goToAboutScreen()
    fun initNotification(studyHour: Hour)
    fun cancelNextNotification()
}