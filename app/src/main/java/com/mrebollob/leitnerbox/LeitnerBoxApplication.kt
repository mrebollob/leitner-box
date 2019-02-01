package com.mrebollob.leitnerbox

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.mrebollob.leitnerbox.di.interactorModule
import com.mrebollob.leitnerbox.di.mainModule
import io.fabric.sdk.android.Fabric
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class LeitnerBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(mainModule, interactorModule))
        iniCalligraphy()
        initializeCrashlytics()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
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
}