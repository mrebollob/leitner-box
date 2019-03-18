package com.mrebollob.leitnerbox.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import com.mrebollob.leitnerbox.domain.extension.navigate
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.FIRST_NOTIFICATION_ALARM_REQUEST_CODE
import com.mrebollob.leitnerbox.presentation.main.countdown.CountdownFragment
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun layoutId(): Int = R.layout.activity_main
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
//                    message.setText(R.string.title_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
//                    message.setText(R.string.title_dashboard)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
//                    message.setText(R.string.title_notifications)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigate(CountdownFragment.newInstance())
    }

    private fun initNotification(studyHour: Hour) {

        val studyTime = studyHour.getCalendarForToday()

        if (studyTime.timeInMillis < System.currentTimeMillis()) {
            studyTime.timeInMillis = studyTime.timeInMillis + ONE_DAY_MILLIS
        }

        val notificationIntent = Intent(this, StudyTimeNotificationReceiver::class.java)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            this,
            FIRST_NOTIFICATION_ALARM_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val am = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
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

    private fun cancelNextNotification() {
        val notificationIntent = Intent(this, StudyTimeNotificationReceiver::class.java)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            this,
            FIRST_NOTIFICATION_ALARM_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(notificationPendingIntent)
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
