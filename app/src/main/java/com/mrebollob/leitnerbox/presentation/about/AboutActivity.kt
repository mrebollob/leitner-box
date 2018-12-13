package com.mrebollob.leitnerbox.presentation.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.mrebollob.leitnerbox.BuildConfig
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.util.extensions.loadCustomTabs
import kotlinx.android.synthetic.main.activity_about.*
import org.koin.android.ext.android.inject

class AboutActivity : BaseActivity(), AboutView {

    val presenter: AboutPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initUI()
        presenter.attachView(this)
    }

    private fun initUI() {
        appVersionView.setValue(BuildConfig.VERSION_NAME)

        sourceCodeView.setOnClickListener { presenter.onSourceCodeClick() }
        privacyPolicyView.setOnClickListener { presenter.onPrivacyPolicyClick() }
        licenseView.setOnClickListener { presenter.onLicenseClick() }
    }

    override fun openWebViewScreen(url: String) {
        Uri.parse(url).loadCustomTabs(this)
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}
