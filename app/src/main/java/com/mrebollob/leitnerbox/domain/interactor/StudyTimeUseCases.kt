package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository


class GetStudyTime constructor(private val repository: Repository) : UseCase<Hour, UseCase.None>() {

    override suspend fun run(params: None) = repository.getStudyTime()
}

class SaveStudyTime constructor(private val repository: Repository) :
    UseCase<Hour, SaveStudyTime.Params>() {

    override suspend fun run(params: Params) = repository.saveStudyTime(params.hour)

    data class Params(val hour: Hour)
}
