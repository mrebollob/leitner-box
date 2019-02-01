package com.mrebollob.leitnerbox.presentation.settings

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.presentation.views.NumberPickerDialog
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
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
        initToolbar()
        currentDayView.setOnClickListener { presenter.onSetCurrentDayClick() }
        notificationHourView.setOnClickListener { presenter.onSetNotificationHourClick() }
        notificationEnableView.setOnCheckedChangeListener { isEnable ->
            presenter.onNotificationEnableClick(isEnable)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun showDaySelector(day: LeitnerDay) {
        val numberPickerDialog = NumberPickerDialog()
        numberPickerDialog.dayValue = day.dayNumber
        numberPickerDialog.listener = { value ->
            presenter.onSetCurrentDay(LeitnerDay(value, Date(0)))
        }
        numberPickerDialog.show(supportFragmentManager, "number_picker")
    }

    override fun showTimeSelector(hour: Hour) {
        val timePickerDialog =
            TimePickerDialog(this, { _, hourOfDay, minute ->
                presenter.onSetStudyTime(Hour(hourOfDay, minute))
            }, hour.hour, hour.minute, true)
        timePickerDialog.show()
    }

    override fun showCurrentDay(day: LeitnerDay) {
        currentDayView.setValue(day.dayNumber.toString())
    }

    override fun showStudyTime(hour: Hour) {
        notificationHourView.setValue(hour.getString())
    }

    override fun showNotificationEnable(isEnable: Boolean) {
        notificationHourView.setSettingEnabled(isEnable)
        notificationEnableView.setValue(isEnable)
    }

    override fun handleFailure(failure: Failure) {
        toast(getString(R.string.generic_error))
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
