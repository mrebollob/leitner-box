package com.mrebollob.leitnerbox.presentation.levels.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.extension.gone
import com.mrebollob.leitnerbox.domain.extension.visible
import com.mrebollob.leitnerbox.domain.model.Level

class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val levelName by lazy { itemView.findViewById(R.id.levelName) as TextView }
    private val levelCard by lazy { itemView.findViewById(R.id.levelCard) as CardView }
    private val emptyView by lazy { itemView.findViewById(R.id.emptyView) as View }

    fun render(level: Level) {
        val color = getBackgroundColor(level)

//        levelName.text = level.name
        levelCard.setCardBackgroundColor(color)
//        funShowActive(level.active, color)
    }

    private fun funShowActive(active: Boolean, @ColorInt color: Int) {
        if (active) {
            emptyView.gone()
            levelName.setTextColor(ContextCompat.getColor(getContext(), R.color.primary))
        } else {
            emptyView.visible()
            levelName.setTextColor(color)
        }
    }

    @ColorInt
    fun getBackgroundColor(level: Level): Int {
        return when (level.day.toString()) {
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