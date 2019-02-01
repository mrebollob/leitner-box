package com.mrebollob.leitnerbox.domain.interactor

import com.mrebollob.leitnerbox.domain.repository.Repository


class GetNotificationEnable constructor(private val repository: Repository) :
    UseCase<Boolean, UseCase.None>() {

    override suspend fun run(params: None) = repository.isNotificationEnabled()
}

class SaveNotificationEnable constructor(private val repository: Repository) :
    UseCase<Boolean, SaveNotificationEnable.Params>() {

    override suspend fun run(params: Params) = repository.saveNotificationEnable(params.enabled)

    data class Params(val enabled: Boolean)
}
