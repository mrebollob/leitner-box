package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.domain.model.Question
import com.mrebollob.leitnerbox.domain.repository.LeitnerRepository
import javax.inject.Inject

class GetHomework @Inject constructor(private val repository: LeitnerRepository) :
    UseCase<Homework, GetHomework.Params>() {

    override suspend fun run(params: Params) = repository.homework(params.day)

    data class Params(val day: Int)
}

class GetQuestions @Inject constructor(private val repository: LeitnerRepository) :
    UseCase<List<Question>, GetQuestions.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Question>> {
        return repository.questions(params.level)
    }

    data class Params(val level: Int)
}


