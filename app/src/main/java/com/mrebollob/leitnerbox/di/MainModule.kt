package com.mrebollob.leitnerbox.di

import com.google.gson.Gson
import com.mrebollob.leitnerbox.data.RepositoryImp
import com.mrebollob.leitnerbox.domain.repository.LeitnerBox
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.presentation.about.AboutPresenter
import com.mrebollob.leitnerbox.presentation.countdown.CountdownPresenter
import com.mrebollob.leitnerbox.presentation.leitnerbox.LeitnerBoxPresenter
import com.mrebollob.leitnerbox.presentation.main.FirstStartHandler
import com.mrebollob.leitnerbox.presentation.main.MainPresenter
import com.mrebollob.leitnerbox.presentation.settings.SettingsPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val mainModule = module {

    factory { Gson() }

    factory { LeitnerBox(get()) }

    single<Repository> { RepositoryImp(androidContext(), get()) }

    factory { FirstStartHandler(androidContext()) }

    factory {
        MainPresenter(
            getStudyTime = get(),
            getNotificationEnabled = get(),
            firstStartHandler = get()
        )
    }

    factory {
        LeitnerBoxPresenter(
            getCurrentDay = get(),
            saveDayCompleted = get(),
            getDayLevels = get()
        )
    }

    factory {
        CountdownPresenter(
            getStudyTime = get(),
            checkDayDayCompleted = get()
        )
    }

    factory {
        SettingsPresenter(
            getStudyTime = get(),
            saveStudyTime = get(),
            getNotificationEnabled = get(),
            saveNotificationEnabled = get()
        )
    }

    factory {
        AboutPresenter()
    }
}
