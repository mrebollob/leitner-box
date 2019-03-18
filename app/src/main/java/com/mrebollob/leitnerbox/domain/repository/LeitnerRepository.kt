package com.mrebollob.leitnerbox.domain.repository

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.domain.model.Question

interface LeitnerRepository {

    suspend fun homework(day: Int): Either<Failure, Homework>

    suspend fun questions(level: Int): Either<Failure, List<Question>>
}