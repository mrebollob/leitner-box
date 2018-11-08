package com.mrebollob.leitnerbox.data.datasource

import android.content.Context
import java.util.*

class LocalDataSourceImp(context: Context) : LocalDataSource {

    companion object {
        const val START_DATE_KEY = "START_DATE_KEY"
        const val LEVELS_COUNT_KEY = "LEVELS_COUNT_KEY"
    }


    private val sharedPreferences = context.getSharedPreferences("leitnerbox", Context.MODE_PRIVATE)


    override suspend fun saveStartDate(startDate: Date) {
        sharedPreferences.edit()
            .putLong(START_DATE_KEY, startDate.time)
            .apply()
    }

    override suspend fun getStartDate(): Date {

        val startDateTime = sharedPreferences.getLong(START_DATE_KEY, 0)

        if (startDateTime == 0L) {
            //TODO return error
            return Date()
        }

        return Date(startDateTime)
    }

    override suspend fun saveLevelsCount(levelsCount: Int) {
        sharedPreferences.edit()
            .putInt(LEVELS_COUNT_KEY, levelsCount)
            .apply()
    }

    override suspend fun getLevelsCount(): Int {
        val levelsCount = sharedPreferences.getInt(LEVELS_COUNT_KEY, -1)

        if (levelsCount == -1) {
            //TODO return error
            return 7
        }

        return levelsCount
    }
}