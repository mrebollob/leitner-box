package com.mrebollob.leitnerbox.di

import com.mrebollob.leitnerbox.data.datasource.LocalDataSource
import com.mrebollob.leitnerbox.data.datasource.LocalDataSourceImp
import com.mrebollob.leitnerbox.data.repository.RepositoryImp
import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.executor.Executor
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.presentation.main.MainPresenter
import com.mrebollob.leitnerbox.presentation.settings.SettingsPresenter
import com.mrebollob.leitnerbox.util.executor.AndroidExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val mainModule = module {

    factory { LeitnerBox() }
    single<Executor> { AndroidExecutor() }
    single<LocalDataSource> { LocalDataSourceImp(androidContext()) }
    single<Repository> { RepositoryImp(get()) }

    factory {
        MainPresenter(
            executor = get(),
            repository = get(),
            leitnerBox = get()
        )
    }

    factory {
        SettingsPresenter(
            executor = get(),
            repository = get()
        )
    }
}
