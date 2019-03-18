package com.mrebollob.leitnerbox.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.domain.extension.failure
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import com.mrebollob.leitnerbox.domain.extension.observe
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.extension.viewModel
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.FIRST_NOTIFICATION_ALARM_REQUEST_CODE
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mainViewModel: MainViewModel

    override fun layoutId(): Int = R.layout.activity_main
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = viewModel(viewModelFactory) {
            observe(studyTime, ::handleStudyTime)
            failure(failure, ::handleError)
        }

        mainViewModel.init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.navigate_to_settings -> {
//                presenter.onSettingsClick()
                true
            }
            R.id.navigate_to_about -> {
//                presenter.onAboutClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun handleStudyTime(studyTime: Hour?) {
        toast("studyTime: $studyTime")
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

    private fun handleError(failure: Failure?) {
        toast(getString(R.string.generic_error))
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
