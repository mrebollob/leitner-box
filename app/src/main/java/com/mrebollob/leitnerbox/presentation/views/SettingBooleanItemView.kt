package com.mrebollob.leitnerbox.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.mrebollob.leitnerbox.R

class SettingBooleanItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val settingTitle: TextView
    private val settingSubTitle: TextView
    private val settingValue: Switch
    private val settingsContainer: LinearLayout
    private var title = ""
    private var subTitle = ""
    private var value = false

    init {

        View.inflate(context, R.layout.setting_boolean_item_view_layout, this)
        settingTitle = findViewById(R.id.settingTitle)
        settingSubTitle = findViewById(R.id.settingSubTitle)
        settingValue = findViewById(R.id.settingValue)
        settingsContainer = findViewById(R.id.settingsContainer)

        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingBooleanItemView, 0, 0)
            .apply {
                try {
                    title = getString(R.styleable.SettingBooleanItemView_settingsBooleanTitle) ?: ""

                    subTitle = getString(R.styleable.SettingBooleanItemView_settingsBooleanSubTitle) ?:
                            ""

                    value = getBoolean(
                        R.styleable.SettingBooleanItemView_settingsBooleanValue,
                        false
                    )

                    settingTitle.text = title
                    settingSubTitle.text = subTitle
                    settingValue.isChecked = value
                    settingValue.isClickable = false

                    settingsContainer.setOnClickListener {
                        settingValue.isChecked = !settingValue.isChecked
                    }
                } finally {
                    recycle()
                }
            }
    }

    fun setTitle(title: String) {
        this.settingTitle.text = title
        this.title = title
    }

    fun setSubTitle(subTitle: String) {
        this.settingSubTitle.text = subTitle
        this.subTitle = subTitle
    }

    fun setValue(value: Boolean) {
        settingValue.isChecked = value
        this.value = value
    }

    fun setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
        settingValue.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    }
}