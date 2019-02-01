package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.domain.extension.empty
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import java.util.*

data class Hour(val hour: Int, val minute: Int, val is24Hour: Boolean = true) {

    fun getString(): String = String.format("%1$02d:%2$02d", hour, minute)

    fun getHoursUntilDate(date: Date): Int {
        val millisUntilDate = getCalendarForToday().time.time - date.time

        val seconds = millisUntilDate / 1000
        return (seconds / 3600).toInt()
    }

    companion object {
        fun empty() = Hour(Int.empty(), Int.empty())
        fun default() = Hour(22, 0)
    }
}