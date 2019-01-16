package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.util.extensions.getCalendarForToday
import java.util.*

val VOID_HOUR = Hour(-1, -1)

data class Hour(val hour: Int, val minute: Int, val is24Hour: Boolean = true) {

    fun getString(): String = String.format("%1$02d:%2$02d", hour, minute)

    fun getHoursUntilDate(date: Date): Int {
        val millisUntilDate = getCalendarForToday().time.time - date.time

        val seconds = millisUntilDate / 1000
        return (seconds / 3600).toInt()
    }
}