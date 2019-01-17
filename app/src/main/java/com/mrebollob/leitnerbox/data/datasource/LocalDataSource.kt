package com.mrebollob.leitnerbox.data.datasource

import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import java.util.*

interface LocalDataSource {

    suspend fun isFirstStart(): Boolean

    suspend fun setFirstStart(isFirstStart: Boolean)

    suspend fun saveStartDate(startDate: Date)

    suspend fun saveStudyTime(hour: Hour)

    suspend fun getStartDate(): Date

    suspend fun getStudyTime(): Hour

    suspend fun saveLevelsCount(levelsCount: Int)

    suspend fun getLevelsCount(): Int

    suspend fun saveNotificationEnable(enable: Boolean)

    suspend fun getNotificationEnable(): Boolean

    suspend fun getLastDayCompleted(): LeitnerDay

    suspend fun saveLastDayCompleted(day: LeitnerDay)
}