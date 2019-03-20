package com.mrebollob.leitnerbox.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrebollob.leitnerbox.di.ViewModelFactory
import com.mrebollob.leitnerbox.di.annotation.ViewModelKey
import com.mrebollob.leitnerbox.presentation.levels.LevelsViewModel
import com.mrebollob.leitnerbox.presentation.main.home.CountdownViewModel
import com.mrebollob.leitnerbox.presentation.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountdownViewModel::class)
    abstract fun bindCountdownViewModel(countdownViewModel: CountdownViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LevelsViewModel::class)
    abstract fun bindLevelsViewModel(levelsViewModel: LevelsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
