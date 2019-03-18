package com.mrebollob.leitnerbox

import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.repository.ConfigRepository
import com.mrebollob.leitnerbox.domain.interactor.isTodayCompleted
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class UseCasesTest {

    @Test
    fun test_day_not_completed() {

        val result = runBlocking {

            val today = Date(ONE_DAY_MILLIS * 3)
            isTodayCompleted(getRepository(), today)
        }

        assertEquals(false, result)
    }

    @Test
    fun test_day_completed() {

        val result = runBlocking {

            val today = Date(500)
            isTodayCompleted(getRepository(), today)
        }

        assertEquals(true, result)
    }

    private fun getRepository(): ConfigRepository {

        return mock {
            onBlocking { getLastDayCompleted() } doReturn LeitnerDay(1, Date(ONE_DAY_MILLIS))
        }
    }
}