package com.mrebollob.leitnerbox.domain.usecase

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository


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


suspend fun getLastDayCompleted(repository: Repository): Int = repository.getLastDayCompleted()

suspend fun saveLastDayCompleted(repository: Repository, dayNumber: Int) =
    repository.saveLastDayCompleted(dayNumber)

suspend fun isDayCompleted(repository: Repository, dayNumber: Int): Boolean {
    val lastDayCompleted = getLastDayCompleted(repository)
    return dayNumber >= lastDayCompleted
}

