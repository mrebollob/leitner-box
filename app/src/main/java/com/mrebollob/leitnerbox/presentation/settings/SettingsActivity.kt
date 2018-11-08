package com.mrebollob.leitnerbox.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.BaseActivity
import org.koin.android.ext.android.inject

class SettingsActivity : BaseActivity(), SettingsView {

    val presenter: SettingsPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initUI()
        presenter.attachView(this)
    }

    private fun initUI() {

    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
