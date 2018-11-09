package com.mrebollob.leitnerbox.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : BaseActivity(), SettingsView {

    val presenter: SettingsPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initUI()
        presenter.attachView(this)
    }

    private fun initUI() {
        levelsNumberView.setOnClickListener { presenter.onSettingsLevelsClick() }
        startDateView.setOnClickListener { presenter.onSettingsStartDateClick() }
    }

    override fun showStartDate(startDate: Date) {
        // TODO extract format date and localize
        val datestring = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(startDate)
        startDateView.setValue(datestring)
    }

    override fun showLevelsCount(levelsCount: Int) {
        levelsNumberView.setValue(levelsCount.toString())
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
