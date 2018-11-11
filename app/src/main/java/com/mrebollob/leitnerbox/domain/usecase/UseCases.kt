package com.mrebollob.leitnerbox.domain.usecase

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import com.mrebollob.leitnerbox.util.extensions.getDaysBetween
import java.util.*


suspend fun getStartDate(repository: Repository): Date = repository.getStartDate()

suspend fun saveStartDate(repository: Repository, startDate: Date) =
    repository.saveStartDate(startDate)

suspend fun getLevelsCount(repository: Repository): Int = repository.getLevelsCount()

suspend fun saveLevelsCount(repository: Repository, levelsCount: Int) =
    repository.saveLevelsCount(levelsCount)

suspend fun getCurrentDay(repository: Repository, now: Date): Int =
    repository.getStartDate().getDaysBetween(now) + 1


suspend fun getLevels(repository: Repository, leitnerBox: LeitnerBox, now: Date): List<Level> {

    val levelCount = repository.getLevelsCount()
    val currentDay = getCurrentDay(repository, now)

    return leitnerBox.getLevelsForDay(levelCount, currentDay)
}

