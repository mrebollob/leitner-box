package com.mrebollob.leitnerbox.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.model.Level
import com.mrebollob.leitnerbox.util.extensions.inflate
import kotlin.properties.Delegates

class LevelsAdapter : RecyclerView.Adapter<LevelViewHolder>() {

    var levels: List<Level> by Delegates.observable(emptyList())
    { prop, old, new -> notifyDataSetChange() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = parent.inflate(R.layout.level_list_item)
        return LevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levels[position]
        holder.render(level)
    }

    override fun getItemCount(): Int {
        return levels.count()
    }

    fun notifyDataSetChange() {
        notifyDataSetChanged()
    }
}