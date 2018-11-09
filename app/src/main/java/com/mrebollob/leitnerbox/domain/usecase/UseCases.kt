package com.mrebollob.leitnerbox.domain.usecase

import com.mrebollob.leitnerbox.domain.LeitnerBox

import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository
import java.util.*

suspend fun getStartDate(repository: Repository): Date = repository.getStartDate()

suspend fun getLevelsCount(repository: Repository): Int = repository.getLevelsCount()

suspend fun getCurrentDay(repository: Repository): Int {

    val startDate = repository.getStartDate()

    return 1
}

suspend fun getLevels(repository: Repository, leitnerBox: LeitnerBox): List<Level> {

    val levelCount = repository.getLevelsCount()
    val currentDay = getCurrentDay(repository)

    return leitnerBox.getLevelsForDay(levelCount, currentDay)
}

