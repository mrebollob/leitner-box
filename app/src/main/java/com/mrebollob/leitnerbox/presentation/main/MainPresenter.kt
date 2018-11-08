package com.mrebollob.leitnerbox.presentation.main


import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getLevels
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val executor: Executor,
    private val repository: Repository,
    private val leitnerBox: LeitnerBox
) : Presenter<MainView> {

    private var view: MainView? = null

    override fun attachView(view: MainView) {
        this.view = view
        getLevelList()
    }

    override fun detachView() {

    }

    fun onSettingsClicked() {
        view?.goToSettingsScreen()
    }

    private fun getLevelList() {
        GlobalScope.launch(context = executor.main) {
            val levels = getLevels(repository, leitnerBox)
            view?.showLevels(levels)
        }
    }
}

interface MainView {
    fun showCurrentNumberDay(currentDay: Int)
    fun showLevels(levels: List<Level>)
    fun goToSettingsScreen()
}