package com.mrebollob.leitnerbox.presentation.splash

import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.failure
import com.mrebollob.leitnerbox.domain.extension.observe
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.extension.viewModel
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity


class SplashActivity : BaseActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun layoutId() = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = viewModel(viewModelFactory) {
            observe(success, ::renderResult)
            failure(failure, ::handleError)
        }

        splashViewModel.init()
    }

    private fun renderResult(loadingFinished: Boolean?) {

        initApp()
    }

    private fun initApp() {
//        MainActivity.open(this)
//        finish()
    }

    private fun handleError(failure: Failure?) {
        toast("Durisimo ErRoR!!")
    }
}