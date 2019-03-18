package com.mrebollob.leitnerbox.di.builder

import com.mrebollob.leitnerbox.presentation.main.countdown.CountdownFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeCountdownFragment(): CountdownFragment
}
