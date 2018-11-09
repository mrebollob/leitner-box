package com.mrebollob.leitnerbox.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mrebollob.leitnerbox.R

class SettingItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val settingTitle: TextView
    private val settingValue: TextView
    private val settingsContainer: LinearLayout
    private var title: String = ""
    private var value: String = ""

    init {

        View.inflate(context, R.layout.setting_item_view_layout, this)
        settingTitle = findViewById(R.id.settingTitle)
        settingValue = findViewById(R.id.settingValue)
        settingsContainer = findViewById(R.id.settingsContainer)

        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingItemView, 0, 0).apply {
            try {
                title = getString(R.styleable.SettingItemView_settingsTitle) ?: ""
                value = getString(R.styleable.SettingItemView_settingsValue) ?: ""

                settingTitle.text = title
                settingValue.text = value
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        this.settingTitle.text = title
        this.value = title
    }

    fun setValue(value: String) {
        this.settingValue.text = value
        this.value = value
    }

    fun setOnClickListener(listener: (View) -> Unit) {
        settingsContainer.setOnClickListener(listener)
    }
}