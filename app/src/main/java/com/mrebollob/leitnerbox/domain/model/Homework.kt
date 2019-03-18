package com.mrebollob.leitnerbox.domain.model

data class Homework(
    val day: Int,
    val levels: List<Level>
)

data class Level(
    val id: String,
    val name: String,
    val value: Int
)