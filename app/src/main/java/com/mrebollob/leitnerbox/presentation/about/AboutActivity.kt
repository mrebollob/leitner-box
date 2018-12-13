package com.mrebollob.leitnerbox.presentation.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.BaseActivity
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

    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}
