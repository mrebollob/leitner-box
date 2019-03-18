package com.mrebollob.leitnerbox

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.mrebollob.leitnerbox.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import javax.inject.Inject

class LeitnerBoxApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger()
        iniCalligraphy()
        initTimber()
        initializeCrashlytics()
    }

    private fun initializeCrashlytics() {
        val crashlyticsCore = CrashlyticsCore.Builder()
            .disabled("debug" == BuildConfig.BUILD_TYPE)
            .build()

        val crashlytics = Crashlytics.Builder()
            .core(crashlyticsCore)
            .build()

        val answers = Answers()

        val fabric = Fabric.Builder(applicationContext)
            .kits(crashlytics, answers)
            .debuggable(BuildConfig.DEBUG)
            .build()

        Fabric.with(fabric)

        Crashlytics.setString("GIT_SHA_KEY", BuildConfig.GIT_SHA)
        Crashlytics.setString("BUILD_TIME", BuildConfig.BUILD_TIME)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun iniCalligraphy() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}