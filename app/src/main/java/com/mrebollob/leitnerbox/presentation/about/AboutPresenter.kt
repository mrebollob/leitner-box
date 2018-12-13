package com.mrebollob.leitnerbox.presentation.about

import com.mrebollob.leitnerbox.presentation.Presenter

private const val SOURCE_CODE_URL = "https://github.com/mrebollob/leitner-box"
private const val PRIVACY_POLICY_URL = "https://github.com/mrebollob/leitner-box"
private const val LICENSE_URL = "https://github.com/mrebollob/leitner-box"

class AboutPresenter() : Presenter<AboutView> {

    private var view: AboutView? = null

    override fun attachView(view: AboutView) {
        this.view = view
    }

    override fun detachView() {

    }

    fun onSourceCodeClick() = view?.openWebViewScreen(SOURCE_CODE_URL)
    fun onPrivacyPolicyClick() = view?.openWebViewScreen(PRIVACY_POLICY_URL)
    fun onLicenseClick() = view?.openWebViewScreen(LICENSE_URL)
}

interface AboutView {
    fun openWebViewScreen(url: String)
}