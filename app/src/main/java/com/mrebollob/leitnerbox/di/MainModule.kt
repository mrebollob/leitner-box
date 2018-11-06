package com.mrebollob.leitnerbox.di

import com.mrebollob.leitnerbox.repository.LocalRepository
import com.mrebollob.leitnerbox.repository.LocalRepositoryImp
import com.mrebollob.leitnerbox.ui.main.MainViewModel
import com.mrebollob.leitnerbox.util.AppExecutors
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {

    single { AppExecutors() }
    single<LocalRepository> { LocalRepositoryImp(get()) }

    viewModel { MainViewModel(get()) }
}
