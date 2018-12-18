package com.mrebollob.leitnerbox.data.repository

import com.mrebollob.leitnerbox.data.datasource.LocalDataSource
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.domain.repository.Repository
import java.util.*

class RepositoryImp(private val local: LocalDataSource) : Repository {


    override suspend fun isFirstStart(): Boolean = local.isFirstStart()

    override suspend fun setFirstStart(isFirstStart: Boolean) = local.setFirstStart(isFirstStart)

    override suspend fun saveStartDate(startDate: Date) = local.saveStartDate(startDate)

    override suspend fun getStartDate(): Date = local.getStartDate()

    override suspend fun saveStudyTime(hour: Hour) = local.saveStudyTime(hour)

    override suspend fun getStudyTime(): Hour = local.getStudyTime()

    override suspend fun saveNotificationEnable(enable: Boolean) =
        local.saveNotificationEnable(enable)

    override suspend fun getNotificationEnable(): Boolean = local.getNotificationEnable()

    override suspend fun saveLevelsCount(levelsCount: Int) = local.saveLevelsCount(levelsCount)

    override suspend fun getLevelsCount(): Int = local.getLevelsCount()
}