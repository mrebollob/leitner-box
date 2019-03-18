package com.mrebollob.leitnerbox.data.entity

import com.mrebollob.leitnerbox.domain.extension.empty
import com.mrebollob.leitnerbox.domain.model.Level


data class LevelEntity(private val day: Int, private val levels: List<Int>) {
    fun toLevel() = Level(day, levels)

    companion object {
        fun empty() = LevelEntity(
            Int.empty(),
            emptyList()
        )
    }
}