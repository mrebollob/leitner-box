package com.mrebollob.leitnerbox

import android.app.Application
import com.mrebollob.leitnerbox.di.mainModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class LeitnerBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(mainModule))
        iniCalligraphy()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
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