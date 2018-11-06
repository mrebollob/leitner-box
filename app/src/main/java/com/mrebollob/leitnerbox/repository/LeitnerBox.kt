package com.mrebollob.leitnerbox.repository

import com.mrebollob.leitnerbox.model.Level

class LeitnerBox {

    fun getLevelsForDay(levelsCount: Int, dayNumber: Int): List<Level> {

        val levels = mutableListOf<Level>()

        for (index in 1..levelsCount) {
            levels.add(Level(index, "$index", isActive(index, dayNumber)))
        }

        return levels
    }

    private fun isActive(index: Int, dayNumber: Int): Boolean {
        return index % 3 == 0
    }
}