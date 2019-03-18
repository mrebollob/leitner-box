package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.ConfigRepository
import javax.inject.Inject


class GetStudyTime @Inject constructor(private val repository: ConfigRepository) :
    UseCase<Hour, UseCase.None>() {

    override suspend fun run(params: None) = repository.getStudyTime()
}

class SaveStudyTime @Inject constructor(private val repository: ConfigRepository) :
    UseCase<Hour, SaveStudyTime.Params>() {

    override suspend fun run(params: Params) = repository.saveStudyTime(params.hour)

    data class Params(val hour: Hour)
}
