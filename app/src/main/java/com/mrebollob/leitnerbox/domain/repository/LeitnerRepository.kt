package com.mrebollob.leitnerbox.domain.repository

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.model.Question

interface LeitnerRepository {

    suspend fun levels(day: Int): Either<Failure, Level>

    suspend fun questions(level: Int): Either<Failure, List<Question>>
}