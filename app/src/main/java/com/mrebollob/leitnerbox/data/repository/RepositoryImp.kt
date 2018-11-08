package com.mrebollob.leitnerbox.data.repository

import com.mrebollob.leitnerbox.data.datasource.LocalDataSource
import com.mrebollob.leitnerbox.domain.repository.Repository
import java.util.*

class RepositoryImp(private val local: LocalDataSource) : Repository {

    override suspend fun saveStartDate(startDate: Date) = local.saveStartDate(startDate)

    override suspend fun getStartDate(): Date = local.getStartDate()

    override suspend fun saveLevelsCount(levelsCount: Int) = local.saveLevelsCount(levelsCount)

    override suspend fun getLevelsCount(): Int = local.getLevelsCount()
}