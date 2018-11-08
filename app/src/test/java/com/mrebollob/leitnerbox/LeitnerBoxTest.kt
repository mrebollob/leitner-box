package com.mrebollob.leitnerbox

import com.mrebollob.leitnerbox.domain.LeitnerBox
import org.junit.Assert
import org.junit.Test

class LeitnerBoxTest {

    @Test
    fun should_get_a_list_of_7_levels() {

        val levelsCount = 7
        val leitnerBox = LeitnerBox()

        val levels = leitnerBox.getLevelsForDay(levelsCount, 1)

        Assert.assertEquals(levelsCount, levels.size)
    }
}