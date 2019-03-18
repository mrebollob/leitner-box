package com.mrebollob.leitnerbox.data


import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeitnerBoxService
@Inject constructor(retrofit: Retrofit) : LeitnerBoxApi {

    private val leitnerBoxApi by lazy { retrofit.create(LeitnerBoxApi::class.java) }

    override fun levels(levelId: Int) = leitnerBoxApi.levels(levelId)

    override fun questions(questionslevel: Int) = leitnerBoxApi.questions(questionslevel)
}