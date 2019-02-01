package com.mrebollob.leitnerbox.presentation.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.NumberPicker
import com.mrebollob.leitnerbox.R
import kotlin.properties.Delegates

class NumberPickerDialog : DialogFragment() {

    var listener: (value: Int) -> Unit = { _ -> }
    private var numberPicker: NumberPicker? = null
    var dayValue: Int by Delegates.observable(0) { _, _, newValue ->
        numberPicker?.value = newValue
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        numberPicker = NumberPicker(activity)
        numberPicker?.apply {
            minValue = 0
            maxValue = 10_000
            wrapSelectorWheel = false
            value = dayValue
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.settings_current_day_title)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            listener(numberPicker?.value ?: 0)
        }

        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
        }

        builder.setView(numberPicker)
        return builder.create()
    }
}