package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.domain.extension.empty

data class Level(
    val index: Int,
    val name: String,
    val active: Boolean = false
) {

    companion object {
        fun empty() = Level(Int.empty(), String.empty())
    }
}