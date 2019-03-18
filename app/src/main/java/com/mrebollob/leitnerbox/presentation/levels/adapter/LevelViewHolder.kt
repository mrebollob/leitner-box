package com.mrebollob.leitnerbox.presentation.levels.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.model.Level

class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val levelName by lazy { itemView.findViewById(R.id.levelName) as TextView }
    private val levelCard by lazy { itemView.findViewById(R.id.levelCard) as CardView }

    fun render(level: Level) {
        val color = getBackgroundColor(level)

        levelName.text = level.name
        levelCard.setCardBackgroundColor(color)
    }

    @ColorInt
    fun getBackgroundColor(level: Level): Int {
        return when (level.name) {
            "1" -> ContextCompat.getColor(getContext(), R.color.level_1)
            "2" -> ContextCompat.getColor(getContext(), R.color.level_2)
            "3" -> ContextCompat.getColor(getContext(), R.color.level_3)
            "4" -> ContextCompat.getColor(getContext(), R.color.level_4)
            "5" -> ContextCompat.getColor(getContext(), R.color.level_5)
            "6" -> ContextCompat.getColor(getContext(), R.color.level_6)
            "7" -> ContextCompat.getColor(getContext(), R.color.level_7)
            "8" -> ContextCompat.getColor(getContext(), R.color.level_8)
            else -> ContextCompat.getColor(getContext(), R.color.level_9)
        }
    }

    private fun getContext(): Context {
        return itemView.context
    }
}