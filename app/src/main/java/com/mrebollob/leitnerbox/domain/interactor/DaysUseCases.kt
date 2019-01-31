package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.repository.Repository
import java.util.*


class GetCurrentDay constructor(private val repository: Repository) :
    UseCase<LeitnerDay, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCurrentDay()
}

class SaveDayCompleted constructor(private val repository: Repository) :
    UseCase<LeitnerDay, SaveDayCompleted.Params>() {

    override suspend fun run(params: Params) = repository.saveDayCompleted(params.day)

    data class Params(val day: LeitnerDay)
}


class CheckDayDayCompleted constructor(private val repository: Repository) :
    UseCase<Boolean, CheckDayDayCompleted.Params>() {

    override suspend fun run(params: Params): Either<Failure, Boolean> {

        return Either.Right(false)
    }

    data class Params(val day: Date)
}
