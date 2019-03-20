package com.mrebollob.leitnerbox.presentation.platform

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
    }

    abstract fun layoutId(): Int

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(it))
        } ?: also {
            super.attachBaseContext(newBase)
        }
    }
}
