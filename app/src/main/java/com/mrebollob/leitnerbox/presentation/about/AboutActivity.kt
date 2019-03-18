package com.mrebollob.leitnerbox.presentation.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.mrebollob.leitnerbox.BuildConfig
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.extension.loadCustomTabs
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.presentation.intro.IntroActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar.*


class AboutActivity : BaseActivity(), AboutView {

//    val presenter: AboutPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initUI()
//        presenter.attachView(this)
    }

    private fun initUI() {
        initToolbar()
        appVersionView.setValue(BuildConfig.VERSION_NAME)

//        sourceCodeView.setOnClickListener { presenter.onSourceCodeClick() }
//        tutorialView.setOnClickListener { presenter.onTutorialViewClick() }
//        recommendedReadingView.setOnClickListener { presenter.onRecommendedReadingViewClick() }
//        licenseView.setOnClickListener { presenter.onLicenseClick() }
    }

    private fun initToolbar() {
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun openWebViewScreen(url: String) {
        loadCustomTabs(Uri.parse(url))
    }

    override fun goToIntroScreen() {
        IntroActivity.open(this)
        finish()
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}
