package com.mrebollob.leitnerbox.presentation.main


import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
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
                view.showLeitnerView()
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
}

interface MainView {
    fun showLeitnerView()
    fun goToIntroScreen()
    fun goToSettingsScreen()
    fun goToAboutScreen()
}