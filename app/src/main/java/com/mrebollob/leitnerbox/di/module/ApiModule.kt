package com.mrebollob.leitnerbox.di.module


import android.app.Application
import com.google.gson.Gson
import com.mrebollob.leitnerbox.BuildConfig
import com.mrebollob.leitnerbox.data.ConfigRepositoryImp
import com.mrebollob.leitnerbox.data.LeitnerRepositoryImp
import com.mrebollob.leitnerbox.data.api.LeitnerBoxApiService
import com.mrebollob.leitnerbox.data.utils.NetworkHandler
import com.mrebollob.leitnerbox.di.annotation.BaseUrl
import com.mrebollob.leitnerbox.domain.repository.ConfigRepository
import com.mrebollob.leitnerbox.domain.repository.LeitnerRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideConfigRepository(
        context: Application, gson: Gson
    ): ConfigRepository = ConfigRepositoryImp(context, gson)

    @Provides
    @Singleton
    fun provideLeitnerRepository(
        networkHandler: NetworkHandler,
        apiService: LeitnerBoxApiService
    ): LeitnerRepository =
        LeitnerRepositoryImp(apiService, networkHandler)

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient, @BaseUrl baseUrl: String
    ): LeitnerBoxApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LeitnerBoxApiService::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}