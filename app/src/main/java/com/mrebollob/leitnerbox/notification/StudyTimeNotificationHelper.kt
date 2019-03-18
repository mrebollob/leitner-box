package com.mrebollob.leitnerbox.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.CURRENT_NOTIFICATION_KEY
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.FIRST_NOTIFICATION_ALARM_REQUEST_CODE
import timber.log.Timber
import java.util.*

class StudyTimeNotificationHelper : BroadcastReceiver() {

//    private val repository = (StandAloneContext.koinContext as KoinContext).get<Repository>()
//    private val getStudyTime = GetStudyTime(repository)
//    private val getNotificationEnabled = GetNotificationEnable(repository)
    @Volatile
    private lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("Device booted, broadcast received, setting bedtime notification")
        this.context = context

//        getNotificationEnabled(UseCase.None()) {
//            it.either(
//                ::handleFailure,
//                ::handleNotificationEnabled
//            )
//        }
    }

    private fun handleNotificationEnabled(isEnable: Boolean) {
        if (isEnable) {
//            getStudyTime(UseCase.None()) {
//                it.either(
//                    ::handleFailure,
//                    ::handleStudyTime
//                )
//            }
        }
    }

    private fun handleStudyTime(studyHour: Hour) {
        val studyTime = studyHour.getCalendarForToday()

        if (studyTime.timeInMillis < System.currentTimeMillis()) {
            studyTime.timeInMillis = studyTime.timeInMillis + ONE_DAY_MILLIS
        }

        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        settings.edit().putInt(CURRENT_NOTIFICATION_KEY, 1).apply()
        setStudyTimeNotification(context, studyTime)
    }

    private fun handleFailure(failure: Failure) {
        Timber.e("Notification error: $failure")
    }

    private fun setStudyTimeNotification(context: Context, studyTime: Calendar) {
        val intent1 = Intent(context, StudyTimeNotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            FIRST_NOTIFICATION_ALARM_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val am = context.getSystemService(ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                studyTime.timeInMillis,
                pendingIntent
            )
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, studyTime.timeInMillis, pendingIntent)
        }
    }
}
