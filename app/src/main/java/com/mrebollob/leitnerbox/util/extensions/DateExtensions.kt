package com.mrebollob.leitnerbox.util.extensions

import com.mrebollob.leitnerbox.domain.model.Hour
import java.util.*
import java.util.Calendar.HOUR


fun Date.getDaysBetween(endDate: Date): Int = with(this) {
    val todayCalendar = Calendar.getInstance()
    todayCalendar.time = this
    todayCalendar.set(HOUR, 0)
    todayCalendar.set(HOUR, 0)


    val different = endDate.time - this.time

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli

    return elapsedDays.toInt()
}

fun Hour.getCalendarForToday(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, this.hour)
    calendar.set(Calendar.MINUTE, this.minute)
    calendar.set(Calendar.SECOND, 0)
    return calendar
}

fun Date.getDaysSinceUnix(): Int = with(this) {
    val todayCalendar = Calendar.getInstance()
    todayCalendar.time = this
    todayCalendar.set(HOUR, 0)
    todayCalendar.set(HOUR, 0)

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = this.time / daysInMilli

    return elapsedDays.toInt()
}