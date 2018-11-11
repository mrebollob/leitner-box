package com.mrebollob.leitnerbox

import com.google.gson.Gson
import com.mrebollob.leitnerbox.domain.LeitnerBox
import org.junit.Assert
import org.junit.Test

class LeitnerBoxTest {

    @Test
    fun test_day_1_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 1)

        Assert.assertEquals(
            arrayListOf(2, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_2_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 2)

        Assert.assertEquals(
            arrayListOf(3, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_10_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 10)

        Assert.assertEquals(
            arrayListOf(3, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_24_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 24)

        Assert.assertEquals(
            arrayListOf(6, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_29_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 29)

        Assert.assertEquals(
            arrayListOf(4, 2, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_56_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 56)

        Assert.assertEquals(
            arrayListOf(7, 1),
            levels.filter { it.active }.map { it.index }.reversed()
        )
    }

    @Test
    fun test_day_64_with_7_levels() {

        val leitnerBox = getLeitnerBox()

        val levels = leitnerBox.getLevelsForDay(7, 64)

        Assert.assertEquals(arrayListOf(1), levels.filter { it.active }.map { it.index }.reversed())
    }

    private fun getLeitnerBox(): LeitnerBox {
        return LeitnerBox(Gson())
    }
}