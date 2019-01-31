package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.domain.extension.empty
import java.util.*

data class LeitnerDay(
    private val number: Int,
    val created: Date = Date()
) {
    val dayNumber: Int = Math.max(0, number)

    companion object {
        fun empty() = LeitnerDay(Int.empty())
    }
}