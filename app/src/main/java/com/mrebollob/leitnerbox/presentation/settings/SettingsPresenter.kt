package com.mrebollob.leitnerbox.presentation.settings

import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.presentation.Presenter

class SettingsPresenter(
    private val executor: Executor,
    private val repository: Repository
) : Presenter<SettingsView> {

    private var view: SettingsView? = null

    override fun attachView(view: SettingsView) {
        this.view = view

    }

    override fun detachView() {

    }
}

interface SettingsView {

}