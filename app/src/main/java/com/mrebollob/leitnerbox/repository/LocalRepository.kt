package com.mrebollob.leitnerbox.repository

import android.arch.lifecycle.LiveData
import com.mrebollob.leitnerbox.model.Level

interface LocalRepository {
    fun getCurrentNumberDay(): LiveData<Int>
    fun getLevels(): LiveData<List<Level>>
}