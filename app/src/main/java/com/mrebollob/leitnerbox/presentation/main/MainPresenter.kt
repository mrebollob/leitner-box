package com.mrebollob.leitnerbox.presentation.main


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

class MainPresenter(
    private val executor: Executor,
    private val repository: Repository,
    private val leitnerBox: LeitnerBox
) : Presenter<MainView> {

    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
        loadLevels()
        loadCurrentDay()
    }

    override fun detachView() {

    }

    fun onSettingsClick() {
        view?.goToSettingsScreen()
    }

    fun onAboutClick() {
        view?.goToAboutScreen()
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

interface MainView {
    fun showCurrentNumberDay(currentDay: Int)
    fun showLevels(levels: List<Level>)
    fun goToSettingsScreen()
    fun goToAboutScreen()
}