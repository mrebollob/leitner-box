package com.mrebollob.leitnerbox.notification

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.domain.usecase.getStudyTime
import com.mrebollob.leitnerbox.presentation.main.MainActivity
import com.mrebollob.leitnerbox.util.extensions.getCalendarForToday
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinContext
import org.koin.standalone.StandAloneContext
import timber.log.Timber
import java.util.*

class StudyTimeNotificationReceiver : BroadcastReceiver() {

    val executor = (StandAloneContext.koinContext as KoinContext).get<Executor>()
    val repository = (StandAloneContext.koinContext as KoinContext).get<Repository>()

    override fun onReceive(context: Context, intent: Intent) {


        GlobalScope.launch(context = executor.main) {

            val studyTime = getStudyTime(repository).getCalendarForToday()

            createNotificationChannel(context)
            showNotification(context, "Titulo de notificación", "Contenido")
            setNextDayNotification(context, studyTime)
        }
    }

    private fun showNotification(context: Context, title: String, content: String) {
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, LAUNCH_APP_REQUEST_CODE, intent, 0)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context, BEDTIME_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_about)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setColorized(true)
            .setColor(context.resources.getColor(R.color.primary))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            val notificationSoundsEnabled = false

            if (notificationSoundsEnabled) {
                mBuilder.setDefaults(Notification.DEFAULT_SOUND)
                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
                mBuilder.setDefaults(Notification.DEFAULT_LIGHTS)
            } else {
                mBuilder.setSound(null)
            }
        }

        notificationManager.notify(NOTIFICATION_REQUEST_CODE, mBuilder.build())
    }

    companion object {

        internal val BEDTIME_CHANNEL_ID = "studyTimeReminders"

        internal val FIRST_NOTIFICATION_ALARM_REQUEST_CODE = 1
        internal val NOTIFICATION_REQUEST_CODE = 2
        internal val LAUNCH_APP_REQUEST_CODE = 6
        internal val ONE_DAY_MILLIS: Long = 86400000

        internal val CURRENT_NOTIFICATION_KEY = "current_notification"

        internal fun setNextDayNotification(context: Context, studyTime: Calendar) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = studyTime.timeInMillis + ONE_DAY_MILLIS
            Timber.d("Setting notification for tomorrow")

            val intent1 = Intent(context, StudyTimeNotificationReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                FIRST_NOTIFICATION_ALARM_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }

        internal fun createNotificationChannel(context: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val description = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(BEDTIME_CHANNEL_ID, name, importance)
                channel.description = description
                channel.setBypassDnd(true)
                channel.enableLights(true)
                //channel.enableVibration(true);
                channel.lightColor = Color.BLUE
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
