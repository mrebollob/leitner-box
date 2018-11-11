package com.mrebollob.leitnerbox.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mrebollob.leitnerbox.domain.model.Level


class LeitnerBox(val gson: Gson) {

    // data from https://ncase.me/remember/
    private var calendarJson = """[
	[2,1],	[3,1],	[2,1],	[4,1], [2,1],	[3,1],	[2,1],	[1],
	[2,1],	[3,1],	[2,1],	[5,1], [4,2,1],	[3,1],	[2,1],	[1],
	[2,1],	[3,1],	[2,1],	[4,1], [2,1],	[3,1],	[2,1],	[6,1],
	[2,1],	[3,1],	[2,1],	[5,1], [4,2,1],	[3,1],	[2,1],	[1],
	[2,1],	[3,1],	[2,1],	[4,1], [2,1],	[3,1],	[2,1],	[1],
	[2,1],	[3,1],	[2,1],	[5,1], [4,2,1],	[3,1],	[2,1],	[1],
	[2,1],	[3,1],	[2,1],	[4,1], [2,1],	[3,1],	[2,1],	[7,1],
	[2,1],	[3,1],	[6,2,1],[5,1], [4,2,1],	[3,1],	[2,1],	[1]
    ]"""

    fun getLevelsForDay(levelsCount: Int, day: Int): List<Level> {

        val levels = mutableListOf<Level>()

        for (index in 1..levelsCount) {
            levels.add(
                Level(
                    index,
                    "$index",
                    isActive(index, getLevelList(day - 1))
                )
            )
        }

        return levels
    }

    private fun isActive(index: Int, activeLevels: List<Int>): Boolean {
        return activeLevels.contains(index)
    }

    private fun getLevelList(day: Int): List<Int> {
        val listType = object : TypeToken<List<List<Int>>>() {}.type
        val data = gson.fromJson<List<List<Int>>>(calendarJson, listType)
        return data[day]
    }
}