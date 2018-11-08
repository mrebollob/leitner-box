package com.mrebollob.leitnerbox.domain.repository

import java.util.*

interface Repository {

    suspend fun saveStartDate(startDate: Date)

    suspend fun getStartDate(): Date

    suspend fun saveLevelsCount(levelsCount: Int)

    suspend fun getLevelsCount(): Int
}