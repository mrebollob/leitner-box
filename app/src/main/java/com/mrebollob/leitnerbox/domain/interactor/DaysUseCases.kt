package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.getDaysBetween
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.functional.map
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.repository.ConfigRepository
import java.util.*
import javax.inject.Inject


class GetCurrentDay @Inject constructor(private val repository: ConfigRepository) :
    UseCase<LeitnerDay, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCurrentDay()
}

class SaveDayCompleted constructor(private val repository: ConfigRepository) :
    UseCase<LeitnerDay, SaveDayCompleted.Params>() {

    override suspend fun run(params: Params) = repository.saveDayCompleted(params.day)

    data class Params(val day: LeitnerDay)
}

class SaveCurrentDay constructor(private val repository: ConfigRepository) :
    UseCase<LeitnerDay, SaveCurrentDay.Params>() {

    override suspend fun run(params: Params) =
        repository.saveCurrentDay(params.day)

    data class Params(val day: LeitnerDay)
}

class CheckDayDayCompleted constructor(private val repository: ConfigRepository) :
    UseCase<Boolean, CheckDayDayCompleted.Params>() {

    override suspend fun run(params: Params): Either<Failure, Boolean> {

        return repository.getCurrentDay().map {
            it.created.getDaysBetween(params.today) <= 0
        }
    }

    data class Params(val today: Date)
}
