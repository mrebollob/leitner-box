package com.mrebollob.leitnerbox.data.entity

import com.mrebollob.leitnerbox.domain.extension.empty
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.domain.model.Level


data class LevelEntity(
    private val day: Int,
    private val levels: List<Int>
) {

    fun toHomework(): Homework {
        val hoLevels = mutableListOf<Level>()

        levels.forEach {
            hoLevels.add(Level("level_$it", "$it", it))
        }

        return Homework(day, hoLevels)
    }

    companion object {
        fun empty() = LevelEntity(
            Int.empty(),
            emptyList()
        )
    }
}