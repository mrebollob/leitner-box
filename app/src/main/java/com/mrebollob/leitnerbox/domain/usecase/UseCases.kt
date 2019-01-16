package com.mrebollob.leitnerbox.domain.usecase

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.util.extensions.getDaysSinceUnix
import java.util.*


suspend fun isFirstStart(repository: Repository): Boolean = repository.isFirstStart()

suspend fun setFirstStart(repository: Repository, isFirstStart: Boolean) =
    repository.setFirstStart(isFirstStart)


suspend fun getStudyTime(repository: Repository): Hour = repository.getStudyTime()

suspend fun saveStudyTime(repository: Repository, hour: Hour) =
    repository.saveStudyTime(hour)


suspend fun saveNotificationEnable(repository: Repository, enable: Boolean) =
    repository.saveNotificationEnable(enable)

suspend fun getNotificationEnable(repository: Repository): Boolean =
    repository.getNotificationEnable()


suspend fun getLevels(leitnerBox: LeitnerBox, dayNumber: Int): List<Level> {
    return leitnerBox.getLevelsForDay(dayNumber)
}

suspend fun getLastDayCompleted(repository: Repository): LeitnerDay =
    repository.getLastDayCompleted()

suspend fun saveLastDayCompleted(repository: Repository, day: LeitnerDay) =
    repository.saveLastDayCompleted(day)

suspend fun isTodayCompleted(repository: Repository, today: Date): Boolean {

    val lastDayCompleted = getLastDayCompleted(repository)

    return lastDayCompleted.date.getDaysSinceUnix() >= today.getDaysSinceUnix()
}

