package com.mrebollob.leitnerbox.di.builder

import com.mrebollob.leitnerbox.presentation.main.home.CountdownFragment
import com.mrebollob.leitnerbox.presentation.main.journal.JournalFragment
import com.mrebollob.leitnerbox.presentation.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): CountdownFragment

    @ContributesAndroidInjector
    abstract fun contributeJournalFragment(): JournalFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}
