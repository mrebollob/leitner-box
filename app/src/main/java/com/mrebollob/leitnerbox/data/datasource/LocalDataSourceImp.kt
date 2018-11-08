package com.mrebollob.leitnerbox.data.datasource

import java.util.*

class LocalDataSourceImp : LocalDataSource {

    override suspend fun saveStartDate(startDate: Date) {

    }

    override suspend fun getStartDate(): Date {
        return Date()
    }

    override suspend fun saveLevelsCount(levelsCount: Int) {

    }

    override suspend fun getLevelsCount(): Int {
        return 7
    }
}