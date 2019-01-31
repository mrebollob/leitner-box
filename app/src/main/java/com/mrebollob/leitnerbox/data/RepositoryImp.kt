package com.mrebollob.leitnerbox.data

import android.content.Context
import com.google.gson.Gson
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.repository.Repository

class RepositoryImp(context: Context, private val gson: Gson) : Repository {

    companion object {
        private const val STUDY_TIME_KEY = "STUDY_TIME_KEY"
        private const val NOTIFICATION_ENABLE_KEY = "NOTIFICATION_ENABLE_KEY"
        private const val LAST_DAY_COMPLETED_KEY = "LAST_DAY_COMPLETED_KEY"
    }

    private val sharedPreferences = context.getSharedPreferences("leitnerbox", Context.MODE_PRIVATE)

    override suspend fun getCurrentDay(): Either<Failure, LeitnerDay> {

        return try {
            val jsonDay = sharedPreferences.getString(LAST_DAY_COMPLETED_KEY, "")

            if (jsonDay.isNullOrEmpty()) {
                Either.Right(LeitnerDay.empty())
            } else {
                val completedDay = gson.fromJson(jsonDay, LeitnerDay::class.java)
                Either.Right(completedDay.copy(number = completedDay.dayNumber + 1))
            }

        } catch (exception: Throwable) {
            sharedPreferences.edit().remove(LAST_DAY_COMPLETED_KEY)
            Either.Left(Failure.EmptyData)
        }
    }

    override suspend fun saveDayCompleted(day: LeitnerDay): Either<Failure, LeitnerDay> {
        sharedPreferences.edit()
            .putString(LAST_DAY_COMPLETED_KEY, gson.toJson(day))
            .apply()

        return Either.Right(day)
    }

    override suspend fun getStudyTime(): Either<Failure, Hour> {
        return try {
            val jsonHour = sharedPreferences.getString(STUDY_TIME_KEY, "")
            val hour = if (jsonHour.isNullOrEmpty()) {
                Hour.default()
            } else {
                gson.fromJson(jsonHour, Hour::class.java)
            }

            Either.Right(hour)

        } catch (exception: Throwable) {
            sharedPreferences.edit().remove(STUDY_TIME_KEY)
            Either.Left(Failure.EmptyData)
        }
    }

    override suspend fun saveStudyTime(hour: Hour): Either<Failure, Hour> {
        sharedPreferences.edit()
            .putString(STUDY_TIME_KEY, gson.toJson(hour))
            .apply()

        return Either.Right(hour)
    }

    override suspend fun saveNotificationEnable(isEnable: Boolean): Either<Failure, Boolean> {
        sharedPreferences.edit()
            .putBoolean(NOTIFICATION_ENABLE_KEY, isEnable)
            .apply()

        return Either.Right(isEnable)
    }

    override suspend fun isNotificationEnabled(): Either<Failure, Boolean> {
        return try {
            Either.Right(sharedPreferences.getBoolean(NOTIFICATION_ENABLE_KEY, true))

        } catch (exception: Throwable) {
            sharedPreferences.edit().remove(NOTIFICATION_ENABLE_KEY)
            Either.Left(Failure.EmptyData)
        }
    }
}