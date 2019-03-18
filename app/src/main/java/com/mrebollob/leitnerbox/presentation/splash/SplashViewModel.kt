package com.mrebollob.leitnerbox.presentation.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.presentation.platform.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    var isFirstStart: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {

        Handler().postDelayed(
            {
                isFirstStart.value = false
            },
            3000
        )
    }
}