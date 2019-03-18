package com.mrebollob.leitnerbox.presentation.splash

import android.content.Context

class FirstStartHandler(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("leitnerbox", Context.MODE_PRIVATE)

    fun isFirstStart(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_START, true)
    }

    fun saveFirstStart() {
        sharedPreferences.edit()
            .putBoolean(IS_FIRST_START, false)
            .apply()
    }

    companion object {
        private const val IS_FIRST_START = "IS_FIRST_START"
    }
}