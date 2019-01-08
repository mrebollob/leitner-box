package com.mrebollob.leitnerbox.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import java.util.*

class LocalDataSourceImp(context: Context, private val gson: Gson) : LocalDataSource {

    companion object {
        private const val FIRST_START_KEY = "FIRST_START_KEY"
        private const val START_DATE_KEY = "START_DATE_KEY"
        private const val STUDY_TIME_KEY = "STUDY_TIME_KEY"
        private const val LEVELS_COUNT_KEY = "LEVELS_COUNT_KEY"
        private const val NOTIFICATION_ENABLE_KEY = "NOTIFICATION_ENABLE_KEY"
        private const val LAST_DAY_COMPLETED_KEY = "LAST_DAY_COMPLETED_KEY"
    }

    private val sharedPreferences = context.getSharedPreferences("leitnerbox", Context.MODE_PRIVATE)

    override suspend fun isFirstStart(): Boolean =
        sharedPreferences.getBoolean(FIRST_START_KEY, true)

    override suspend fun setFirstStart(isFirstStart: Boolean) {
        sharedPreferences.edit()
            .putBoolean(FIRST_START_KEY, isFirstStart)
            .apply()
    }

    override suspend fun saveStartDate(startDate: Date) {
        sharedPreferences.edit()
            .putLong(START_DATE_KEY, startDate.time)
            .apply()
    }

    override suspend fun saveStudyTime(hour: Hour) {
        sharedPreferences.edit()
            .putString(STUDY_TIME_KEY, gson.toJson(hour))
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

    override suspend fun getStudyTime(): Hour {
        val jsonHour = sharedPreferences.getString(STUDY_TIME_KEY, "")

        if (jsonHour.isNullOrEmpty()) {
            //TODO return error
            return Hour(22, 0)
        }

        return gson.fromJson(jsonHour, Hour::class.java)
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

    override suspend fun saveNotificationEnable(enable: Boolean) {
        sharedPreferences.edit()
            .putBoolean(NOTIFICATION_ENABLE_KEY, enable)
            .apply()
    }

    override suspend fun getNotificationEnable(): Boolean =
        sharedPreferences.getBoolean(NOTIFICATION_ENABLE_KEY, true)

    override suspend fun getLastDayCompleted(): LeitnerDay {
        val jsonDay = sharedPreferences.getString(LAST_DAY_COMPLETED_KEY, "")

        if (jsonDay.isNullOrEmpty()) {
            //TODO return error
            return LeitnerDay(0, Date(0))
        }

        return gson.fromJson(jsonDay, LeitnerDay::class.java)
    }

    override suspend fun saveLastDayCompleted(day: LeitnerDay) {
        sharedPreferences.edit()
            .putString(LAST_DAY_COMPLETED_KEY, gson.toJson(day))
            .apply()
    }
}