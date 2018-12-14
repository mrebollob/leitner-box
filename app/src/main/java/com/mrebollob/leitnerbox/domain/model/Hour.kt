package com.mrebollob.leitnerbox.domain.model

data class Hour(val hour: Int, val minute: Int, val is24Hour: Boolean = true) {

    fun getString(): String = "$hour:$minute"
}