package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.ConfigRepository
import java.util.*
import javax.inject.Inject

class GetNextStudyTime @Inject constructor(private val repository: ConfigRepository) :
    UseCase<Date, UseCase.None>() {

    override suspend fun run(params: None) = repository.getNextStudyTime()
}

class GetStudyHour @Inject constructor(private val repository: ConfigRepository) :
    UseCase<Hour, UseCase.None>() {

    override suspend fun run(params: None) = repository.getStudyHour()
}

class SaveStudyHour @Inject constructor(private val repository: ConfigRepository) :
    UseCase<Hour, SaveStudyHour.Params>() {

    override suspend fun run(params: Params) = repository.saveStudyHour(params.hour)

    data class Params(val hour: Hour)
}
