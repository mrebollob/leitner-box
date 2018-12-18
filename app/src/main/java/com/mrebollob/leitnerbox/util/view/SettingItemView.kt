package com.mrebollob.leitnerbox.util.view

import android.content.Context
import android.support.v4.content.ContextCompat
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
    private var settingsEnabled: Boolean = true
    private var listener: (View) -> Unit = {}

    init {

        View.inflate(context, R.layout.setting_item_view_layout, this)
        settingTitle = findViewById(R.id.settingTitle)
        settingValue = findViewById(R.id.settingValue)
        settingsContainer = findViewById(R.id.settingsContainer)

        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingItemView, 0, 0).apply {
            try {
                title = getString(R.styleable.SettingItemView_settingsTitle) ?: ""
                value = getString(R.styleable.SettingItemView_settingsValue) ?: ""
                settingsEnabled = getBoolean(R.styleable.SettingItemView_settingsEnabled, true)

                settingTitle.text = title
                settingValue.text = value
                settingsContainer.setOnClickListener(listener)
                setSettingEnabled(settingsEnabled)
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        this.settingTitle.text = title
        this.title = title
    }

    fun setValue(value: String) {
        this.settingValue.text = value
        this.value = value
    }

    fun setSettingEnabled(settingsEnabled: Boolean) {
        if (settingsEnabled) {
            settingTitle.setTextColor(ContextCompat.getColor(context, R.color.primary_text))
            settingsContainer.setOnClickListener(listener)
        } else {
            settingTitle.setTextColor(ContextCompat.getColor(context, R.color.secondary_text))
            settingsContainer.setOnClickListener(null)
        }
    }

    fun setOnClickListener(listener: (View) -> Unit) {
        this.listener = listener
        if (settingsEnabled) {
            settingsContainer.setOnClickListener(listener)
        }
    }
}