package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.repository.LeitnerBox

class GetDayLevels constructor(private val leitnerBox: LeitnerBox) :
    UseCase<List<Level>, GetDayLevels.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Level>> {
        return Either.Right(leitnerBox.getLevelsForDay(params.day))
    }

    data class Params(val day: LeitnerDay)
}
