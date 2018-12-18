package com.mrebollob.leitnerbox.domain.repository

import com.mrebollob.leitnerbox.domain.model.Hour
import java.util.*

interface Repository {

    suspend fun isFirstStart(): Boolean

    suspend fun setFirstStart(isFirstStart: Boolean)

    suspend fun saveStartDate(startDate: Date)

    suspend fun getStartDate(): Date

    suspend fun saveStudyTime(hour: Hour)

    suspend fun getStudyTime(): Hour

    suspend fun saveNotificationEnable(enable: Boolean)

    suspend fun getNotificationEnable(): Boolean

    suspend fun saveLevelsCount(levelsCount: Int)

    suspend fun getLevelsCount(): Int
}