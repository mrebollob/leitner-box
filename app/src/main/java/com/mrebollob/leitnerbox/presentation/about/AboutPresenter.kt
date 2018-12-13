package com.mrebollob.leitnerbox.presentation.about

import com.mrebollob.leitnerbox.presentation.Presenter

class AboutPresenter() : Presenter<AboutView> {

    private var view: AboutView? = null

    override fun attachView(view: AboutView) {
        this.view = view
    }

    override fun detachView() {

    }

    fun onSourceCodeClick() = view?.goToSourceCodeScreen()
    fun onPrivacyPolicyClick() = view?.goToPrivacyPolicyScreen()
    fun onLicenseClick() = view?.goToLicenseScreen()
}

interface AboutView {
    fun goToSourceCodeScreen()
    fun goToPrivacyPolicyScreen()
    fun goToLicenseScreen()
}