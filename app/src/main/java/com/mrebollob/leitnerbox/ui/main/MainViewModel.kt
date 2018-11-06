package com.mrebollob.leitnerbox.ui.main

import android.arch.lifecycle.ViewModel
import com.mrebollob.leitnerbox.repository.LocalRepository

class MainViewModel(private val localRepository: LocalRepository) : ViewModel() {

    fun getCurrentNumberDay() = localRepository.getCurrentNumberDay()

    fun getLevels() = localRepository.getLevels()
}