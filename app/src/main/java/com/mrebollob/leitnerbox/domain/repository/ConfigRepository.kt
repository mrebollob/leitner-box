package com.mrebollob.leitnerbox.domain.repository

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import java.util.*

interface ConfigRepository {

    suspend fun completedDay(): Either<Failure, LeitnerDay>

    suspend fun saveCompletedDay(day: LeitnerDay): Either<Failure, LeitnerDay>

    suspend fun getNextStudyTime(): Either<Failure, Date>

    suspend fun getStudyHour(): Either<Failure, Hour>

    suspend fun saveStudyHour(hour: Hour): Either<Failure, Hour>

    suspend fun saveNotificationEnable(isEnable: Boolean): Either<Failure, Boolean>

    suspend fun isNotificationEnabled(): Either<Failure, Boolean>
}