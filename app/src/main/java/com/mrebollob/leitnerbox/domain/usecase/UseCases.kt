package com.mrebollob.leitnerbox.domain.usecase

import com.mrebollob.leitnerbox.domain.LeitnerBox
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.Repository

suspend fun getCurrentDay(repository: Repository): Int {

    val startDate = repository.getStartDate()

    return 1
}

suspend fun getLevels(repository: Repository, leitnerBox: LeitnerBox): List<Level> {

    val levelCount = repository.getLevelsCount()
    val currentDay = getCurrentDay(repository)

    return leitnerBox.getLevelsForDay(levelCount, currentDay)
}