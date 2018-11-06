package com.mrebollob.leitnerbox.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.model.Level
import com.mrebollob.leitnerbox.util.AppExecutors

class LocalRepositoryImp(private val appExecutors: AppExecutors) : LocalRepository {


    override fun getCurrentNumberDay(): LiveData<Int> {
        val currentDayLiveData = MutableLiveData<Int>()
        currentDayLiveData.postValue(getCurrentDay())
        return currentDayLiveData
    }

    override fun getLevels(): LiveData<List<Level>> {

        val levelsLiveData = MutableLiveData<List<Level>>()
        levelsLiveData.postValue(getMockLevels())
        return levelsLiveData
    }

    private fun getCurrentDay(): Int {
        return 2
    }

    private fun getMockLevels(): List<Level> {
        val levels = mutableListOf<Level>()

        levels.add(Level(1, "1", true))
        levels.add(Level(2, "2"))
        levels.add(Level(3, "3"))
        levels.add(Level(4, "4", true))
        levels.add(Level(5, "5"))
        levels.add(Level(6, "6"))
        levels.add(Level(7, "7"))

        return levels
    }
}