package com.mrebollob.leitnerbox.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.model.Level
import com.mrebollob.leitnerbox.model.Resource
import com.mrebollob.leitnerbox.util.AppExecutors

class LocalRepositoryImp(private val appExecutors: AppExecutors) : LocalRepository {

    override fun getLevels(): LiveData<Resource<List<Level>>> {

        val dateLiveData = MutableLiveData<Resource<List<Level>>>()
        dateLiveData.postValue(Resource.loading(null))

        appExecutors.diskIO().execute {
            val levels = getMockLevels()

            appExecutors.mainThread().execute {
                dateLiveData.postValue(Resource.success(levels))
            }
        }

        return dateLiveData
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