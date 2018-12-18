package com.mrebollob.leitnerbox.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getNotificationEnable
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.CURRENT_NOTIFICATION_KEY
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.FIRST_NOTIFICATION_ALARM_REQUEST_CODE
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.util.extensions.getCalendarForToday
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinContext
import org.koin.standalone.StandAloneContext
import timber.log.Timber
import java.util.*

class StudyTimeNotificationHelper : BroadcastReceiver() {

    val executor = (StandAloneContext.koinContext as KoinContext).get<Executor>()
    val repository = (StandAloneContext.koinContext as KoinContext).get<Repository>()

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("Device booted, broadcast received, setting bedtime notification")

        val settings = PreferenceManager.getDefaultSharedPreferences(context)

        GlobalScope.launch(context = executor.main) {
            val notificationsEnabled = getNotificationEnable(repository)

            if (notificationsEnabled) {
                val studyTime = getStudyTime(repository).getCalendarForToday()

                if (studyTime.timeInMillis < System.currentTimeMillis()) {
                    studyTime.timeInMillis = studyTime.timeInMillis + ONE_DAY_MILLIS
                }

                settings.edit().putInt(CURRENT_NOTIFICATION_KEY, 1).apply()
                setStudyTimeNotification(context, studyTime)
            }
        }
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
