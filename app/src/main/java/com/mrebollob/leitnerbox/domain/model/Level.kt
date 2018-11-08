package com.mrebollob.leitnerbox.domain.model

data class Level(
    val index: Int,
    val name: String,
    val active: Boolean = false
)