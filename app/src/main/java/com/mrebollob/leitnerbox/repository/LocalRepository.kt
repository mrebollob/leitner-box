package com.mrebollob.leitnerbox.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mrebollob.leitnerbox.model.Level
import com.mrebollob.leitnerbox.model.Resource
import java.util.*

interface LocalRepository {
    fun getLevels(): LiveData<Resource<List<Level>>>
}