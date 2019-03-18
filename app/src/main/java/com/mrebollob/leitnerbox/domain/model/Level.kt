package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.domain.extension.empty

data class Level(val day: Int, val levels: List<Int>) {

    companion object {
        fun empty() = Level(Int.empty(), arrayListOf())
    }
}