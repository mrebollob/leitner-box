package com.mrebollob.leitnerbox.di.component

import android.app.Application
import com.mrebollob.leitnerbox.LeitnerBoxApplication
import com.mrebollob.leitnerbox.di.builder.ActivityBuilder
import com.mrebollob.leitnerbox.di.module.ApiModule
import com.mrebollob.leitnerbox.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ActivityBuilder::class]
)
interface AppComponent {

    fun inject(app: LeitnerBoxApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

    }
}
