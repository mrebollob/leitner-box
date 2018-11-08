package com.mrebollob.leitnerbox.presentation.main

import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.presentation.main.adapter.LevelsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity(), MainView {

    val presenter: MainPresenter by inject()
    private val levelsAdapter = LevelsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        presenter.attachView(this)
    }

    private fun initUI() {
//        setSupportActionBar(toolbar)
        initRecyclerView()
    }

    override fun showCurrentNumberDay(currentDay: Int) {
        dayTextView.text = getString(R.string.main_view_day_number, currentDay)
    }

    override fun showLevels(levels: List<Level>) {
        levelsAdapter.levels = levels
        val names = levels.filter { it.active }.map { it.name }.reversed()
            .reduce { acc, string -> "$acc, $string" }
        levelsTextView.text = getString(R.string.main_view_levels_to_review, names)
    }

    override fun goToSettingsScreen() {
        TODO("not implemented")
    }

    private fun initRecyclerView() {
        levelsListView.adapter = levelsAdapter
    }
}
