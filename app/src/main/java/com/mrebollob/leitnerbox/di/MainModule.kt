package com.mrebollob.leitnerbox.di

import com.mrebollob.leitnerbox.repository.LeitnerBox
import com.mrebollob.leitnerbox.repository.LocalRepository
import com.mrebollob.leitnerbox.repository.LocalRepositoryImp
import com.mrebollob.leitnerbox.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {

    single { LeitnerBox() }
    single<LocalRepository> { LocalRepositoryImp(get()) }

    viewModel { MainViewModel(get()) }
}
