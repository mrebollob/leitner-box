package com.mrebollob.leitnerbox.data

import com.mrebollob.leitnerbox.data.entity.LevelEntity
import com.mrebollob.leitnerbox.data.entity.QuestionEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface LeitnerBoxApi {
    companion object {
        private const val PARAM_LEVELS = "levelId"
        private const val PARAM_QUESTIONS = "questionslevel"
        private const val LEVELS = "/api/levels/day/{$PARAM_LEVELS}"
        private const val QUESTIONS = "/api/questions/level/{$PARAM_QUESTIONS}"
    }

    @GET(LEVELS)
    fun levels(@Path(PARAM_LEVELS) levelId: Int): Call<LevelEntity>

    @GET(QUESTIONS)
    fun questions(@Path(PARAM_QUESTIONS) questionslevel: Int): Call<List<QuestionEntity>>
}