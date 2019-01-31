package com.mrebollob.leitnerbox.di

import com.mrebollob.leitnerbox.domain.interactor.CheckDayDayCompleted
import com.mrebollob.leitnerbox.domain.interactor.GetCurrentDay
import com.mrebollob.leitnerbox.domain.interactor.GetDayLevels
import com.mrebollob.leitnerbox.domain.interactor.GetNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.GetStudyTime
import com.mrebollob.leitnerbox.domain.interactor.SaveDayCompleted
import com.mrebollob.leitnerbox.domain.interactor.SaveNotificationEnable
import com.mrebollob.leitnerbox.domain.interactor.SaveStudyTime
import com.mrebollob.leitnerbox.presentation.main.FirstStartHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val interactorModule = module {

    factory { GetCurrentDay(get()) }

    factory { SaveDayCompleted(get()) }

    factory { CheckDayDayCompleted(get()) }

    factory { GetDayLevels(get()) }

    factory { GetNotificationEnable(get()) }

    factory { SaveNotificationEnable(get()) }

    factory { GetStudyTime(get()) }

    factory { SaveStudyTime(get()) }
}
