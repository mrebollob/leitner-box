package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.model.Question
import com.mrebollob.leitnerbox.domain.repository.LeitnerRepository

class GetDayLevels constructor(private val repository: LeitnerRepository) :
    UseCase<Level, GetDayLevels.Params>() {

    override suspend fun run(params: Params): Either<Failure, Level> {
        return repository.levels(params.day)
    }

    data class Params(val day: Int)
}

class GetQuestions constructor(private val repository: LeitnerRepository) :
    UseCase<List<Question>, GetQuestions.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Question>> {
        return repository.questions(params.level)
    }

    data class Params(val level: Int)
}


