package com.mrebollob.leitnerbox.notification

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import com.mrebollob.leitnerbox.domain.model.Hour
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(private val context: Application) {


    fun initNotification(studyHour: Hour) {

        val studyTime = studyHour.getCalendarForToday()

        if (studyTime.timeInMillis < System.currentTimeMillis()) {
            studyTime.timeInMillis = studyTime.timeInMillis + ONE_DAY_MILLIS
        }

        val notificationIntent = Intent(context, StudyTimeNotificationReceiver::class.java)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            context,
            StudyTimeNotificationReceiver.FIRST_NOTIFICATION_ALARM_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                studyTime.timeInMillis,
                notificationPendingIntent
            )
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, studyTime.timeInMillis, notificationPendingIntent)
        }
    }

    fun cancelNextNotification() {
        val notificationIntent = Intent(context, StudyTimeNotificationReceiver::class.java)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            context,
            StudyTimeNotificationReceiver.FIRST_NOTIFICATION_ALARM_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(notificationPendingIntent)
    }
}