package com.mrebollob.leitnerbox.di.builder

import com.mrebollob.leitnerbox.presentation.levels.LevelsActivity
import com.mrebollob.leitnerbox.presentation.main.MainActivity
import com.mrebollob.leitnerbox.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    abstract fun contributeLevelsActivity(): LevelsActivity
}