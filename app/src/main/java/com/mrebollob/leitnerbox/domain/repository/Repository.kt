package com.mrebollob.leitnerbox.domain.repository

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.model.LeitnerDay

interface Repository {

    suspend fun getCurrentDay(): Either<Failure, LeitnerDay>

    suspend fun saveDayCompleted(day: LeitnerDay): Either<Failure, LeitnerDay>

    suspend fun saveCurrentDay(day: LeitnerDay): Either<Failure, LeitnerDay>

    suspend fun saveStudyTime(hour: Hour): Either<Failure, Hour>

    suspend fun getStudyTime(): Either<Failure, Hour>

    suspend fun saveNotificationEnable(isEnable: Boolean): Either<Failure, Boolean>

    suspend fun isNotificationEnabled(): Either<Failure, Boolean>
}