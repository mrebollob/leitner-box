package com.mrebollob.leitnerbox.presentation.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.domain.interactor.GetNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.GetStudyHour
import com.mrebollob.leitnerbox.domain.interactor.UseCase
import com.mrebollob.leitnerbox.notification.NotificationManager
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val getNotificationEnable: GetNotificationEnable,
    private val getStudyHour: GetStudyHour,
    private val notificationManager: NotificationManager
) : BaseViewModel() {

    var isFirstStart: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        getNotificationEnable(UseCase.None()) {
            it.either(::handleFailure, ::handleNotificationEnable)
        }

        Handler().postDelayed(
            {
                isFirstStart.value = false
            },
            1000
        )
    }

    private fun handleNotificationEnable(isEnabled: Boolean) {
        if (isEnabled) {
            getStudyHour(UseCase.None()) {
                it.either(
                    ::handleFailure
                ) { hour ->
                    Timber.d("initNotification $hour")
                    notificationManager.initNotification(hour)
                }
            }
        } else {
            Timber.d("cancelNextNotification")
            notificationManager.cancelNextNotification()
        }
    }
}