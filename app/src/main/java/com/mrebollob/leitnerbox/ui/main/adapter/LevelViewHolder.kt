package com.mrebollob.leitnerbox.ui.main.adapter

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.model.Level
import com.mrebollob.leitnerbox.util.extensions.gone
import com.mrebollob.leitnerbox.util.extensions.visible

class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val levelName by lazy { itemView.findViewById(R.id.levelName) as TextView }
    private val levelCard by lazy { itemView.findViewById(R.id.levelCard) as CardView }
    private val emptyView by lazy { itemView.findViewById(R.id.emptyView) as View }

    fun render(level: Level) {
        val color = getBackgroundColor(level)

        levelName.text = level.name
        levelCard.setCardBackgroundColor(color)
        funShowActive(level.active, color)
    }

    private fun funShowActive(active: Boolean, @ColorInt color: Int) {
        if (active) {
            emptyView.gone()
            levelName.setTextColor(ContextCompat.getColor(getContext(), R.color.white_text))
        } else {
            emptyView.visible()
            levelName.setTextColor(color)
        }
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
            else -> ContextCompat.getColor(getContext(), R.color.level_8)
        }
    }

    private fun getContext(): Context {
        return itemView.context
    }
}