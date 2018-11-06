package com.mrebollob.leitnerbox.repository

class LeitnerBox(private val levels: Int) {

    private val intervals = mutableMapOf<Int, Int>()

    init {
        var gap = INITIAL_GAP

        for (level in 1..levels) {
            intervals[level] = gap
            println("level $level -> $gap")
            gap *= 2
        }
    }

    fun getLevelsForDay(dayNumber: Int): List<Int> {

        val levelList = arrayListOf<Int>()

        val testDay = dayNumber + 1

        for (level in levels downTo 1) {

            val interval = intervals[level] ?: 1

            if (testDay % interval == 0) {
                levelList.add(level)
            }
        }

        return levelList
    }

    companion object {
        private const val INITIAL_GAP = 1
    }
}