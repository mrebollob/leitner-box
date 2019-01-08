package com.mrebollob.leitnerbox.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver
import com.mrebollob.leitnerbox.notification.StudyTimeNotificationReceiver.Companion.FIRST_NOTIFICATION_ALARM_REQUEST_CODE
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.presentation.about.AboutActivity
import com.mrebollob.leitnerbox.presentation.countdown.CountdownFragment
import com.mrebollob.leitnerbox.presentation.intro.IntroActivity
import com.mrebollob.leitnerbox.presentation.leitnerbox.LeitnerBoxFragment
import com.mrebollob.leitnerbox.presentation.settings.SettingsActivity
import com.mrebollob.leitnerbox.util.extensions.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.util.extensions.getCalendarForToday
import com.mrebollob.leitnerbox.util.extensions.replaceFragment
import com.mrebollob.leitnerbox.util.extensions.replaceFragmentWithAnimation
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity(), MainView, LeitnerBoxFragment.LeitnerBoxFragmentListener,
    CountdownFragment.CountdownListener {

    val presenter: MainPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.refreshConfig()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.navigate_to_settings -> {
                presenter.onSettingsClick()
                true
            }
            R.id.navigate_to_about -> {
                presenter.onAboutClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showLeitnerView() {
        val fragment = LeitnerBoxFragment.newInstance()
        replaceFragmentWithAnimation(fragment, R.id.container)
    }

    override fun onDayCompleted() {
        presenter.onDayCompleted()
    }

    override fun showCountdownView(withAnimation: Boolean) {
        val fragment = CountdownFragment.newInstance()

        if (withAnimation) {
            replaceFragmentWithAnimation(fragment, R.id.container)
        } else {
            replaceFragment(fragment, R.id.container)
        }
    }

    override fun onGoToLeitnerBoxScreenClick() {
        presenter.onShowLeitnerBoxClick()
    }

    override fun goToIntroScreen() {
        IntroActivity.open(this)
        finish()
    }

    override fun goToSettingsScreen() {
        SettingsActivity.open(this)
    }

    override fun goToAboutScreen() {
        AboutActivity.open(this)
    }

    override fun initNotification(studyHour: Hour) {

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

    override fun cancelNextNotification() {
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
}
