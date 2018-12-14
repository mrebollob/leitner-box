package com.mrebollob.leitnerbox.presentation.about

import com.mrebollob.leitnerbox.presentation.Presenter

private const val SOURCE_CODE_URL = "https://github.com/mrebollob/leitner-box"
private const val LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0"

class AboutPresenter : Presenter<AboutView> {

    private var view: AboutView? = null

    override fun attachView(view: AboutView) {
        this.view = view
    }

    override fun detachView() {

    }

    fun onSourceCodeClick() = view?.openWebViewScreen(SOURCE_CODE_URL)
    fun onLicenseClick() = view?.openWebViewScreen(LICENSE_URL)
}

interface AboutView {
    fun openWebViewScreen(url: String)
}