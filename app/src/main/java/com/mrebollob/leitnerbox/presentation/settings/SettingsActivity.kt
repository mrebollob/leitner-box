package com.mrebollob.leitnerbox.presentation.settings

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
        levelsNumberView.setOnClickListener { presenter.onSetLevelsClick() }
        startDateView.setOnClickListener { presenter.onSetStartDateClick() }
        notificationHourView.setOnClickListener { presenter.onSetNotificationHourClick() }
    }

    override fun showLevelsCountSelector(levelsCount: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.settings_levels)

        val selectedItem = when (levelsCount) {
            3 -> 0
            5 -> 1
            7 -> 2
            else -> 3
        }

        val items = resources.getStringArray(R.array.levels_options)
        builder.setSingleChoiceItems(items, selectedItem) { dialog, pos ->
            presenter.onSetLevelsCount(3 + pos * 2)
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun showDateSelector(startDate: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        val datePickerDialog = DatePickerDialog(
            this, { _, year, month, day ->
                val resultCalendar = Calendar.getInstance()
                resultCalendar.set(Calendar.YEAR, year)
                resultCalendar.set(Calendar.MONTH, month)
                resultCalendar.set(Calendar.DAY_OF_MONTH, day)
                presenter.onSetStartDate(resultCalendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    override fun showTimeSelector(hour: Int, minute: Int) {
        val timePickerDialog =
            TimePickerDialog(this, { _, hourOfDay, minute ->
                presenter.onSetStudyTime(hourOfDay, minute)
            }, hour, minute, true)
        timePickerDialog.show()
    }

    override fun showStartDate(startDate: Date) {
        // TODO extract format date and localize
        val datestring = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate)
        startDateView.setValue(datestring)
    }

    override fun showStudyTime(hour: Int, minute: Int) {
        notificationHourView.setValue("$hour:$minute")
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
