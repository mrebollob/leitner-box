package com.mrebollob.leitnerbox.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrebollob.leitnerbox.di.ViewModelFactory
import com.mrebollob.leitnerbox.di.annotation.ViewModelKey
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
    abstract fun bindUserViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
