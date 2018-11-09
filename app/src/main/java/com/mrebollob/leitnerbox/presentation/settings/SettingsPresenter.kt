package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getLevelsCount
import com.mrebollob.leitnerbox.domain.usecase.getStartDate
import com.mrebollob.leitnerbox.presentation.Presenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class SettingsPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<SettingsView> {

    private var view: SettingsView? = null

    override fun attachView(view: SettingsView) {
        this.view = view

        GlobalScope.launch(context = executor.main) {
            view.showLevelsCount(getLevelsCount(repository))
            view.showStartDate(getStartDate(repository))
        }
    }

    override fun detachView() {

    }

    fun onSettingsLevelsClick() {

    }

    fun onSettingsStartDateClick() {

    }
}

interface SettingsView {
    fun showStartDate(startDate: Date)
    fun showLevelsCount(levelsCount: Int)
}