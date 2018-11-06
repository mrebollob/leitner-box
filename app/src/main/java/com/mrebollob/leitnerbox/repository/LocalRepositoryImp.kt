package com.mrebollob.leitnerbox.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.model.Level

class LocalRepositoryImp(private val leitnerBox: LeitnerBox) : LocalRepository {


    override fun getCurrentNumberDay(): LiveData<Int> {
        val currentDayLiveData = MutableLiveData<Int>()
        currentDayLiveData.postValue(getCurrentDay())
        return currentDayLiveData
    }

    override fun getLevels(): LiveData<List<Level>> {
        val levelsLiveData = MutableLiveData<List<Level>>()
        levelsLiveData.postValue(leitnerBox.getLevelsForDay(getLevelsCount(), getCurrentDay()))
        return levelsLiveData
    }

    private fun getCurrentDay(): Int {
        return 2
    }

    private fun getLevelsCount(): Int {
        return 7
    }
}