package com.mrebollob.leitnerbox.data.datasource

import java.util.*

interface LocalDataSource {

    suspend fun saveStartDate(startDate: Date)

    suspend fun getStartDate(): Date

    suspend fun saveLevelsCount(levelsCount: Int)

    suspend fun getLevelsCount(): Int
}