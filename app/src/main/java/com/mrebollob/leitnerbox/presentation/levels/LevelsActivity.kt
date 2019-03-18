package com.mrebollob.leitnerbox.presentation.levels

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.failure
import com.mrebollob.leitnerbox.domain.extension.observe
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.extension.viewModel
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.levels.adapter.LevelsAdapter
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_levels.*

class LevelsActivity : BaseActivity() {

    private lateinit var levelsViewModel: LevelsViewModel
    private val levelsAdapter = LevelsAdapter()

    override fun layoutId() = R.layout.activity_levels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        levelsViewModel = viewModel(viewModelFactory) {
            observe(levels, ::handleLevels)
            failure(failure, ::handleError)
        }

        levelsViewModel.setDay(2)
    }

    private fun handleLevels(levels: Level?) {
        levels ?: return

        toast("Tengo levels ${levels.day}")

//        levelsAdapter.levels = levels

        val names = levels.levels.map { it.toString() }.reversed()
            .reduce { acc, string -> "$acc, $string" }
        levelsTextView.text = getString(R.string.main_view_levels_to_review, names)
        dayTextView.text = getString(R.string.main_view_day_number, levels.day)
    }

    private fun handleError(failure: Failure?) {
        toast(getString(R.string.generic_error))
    }

    companion object Navigator {
        fun open(context: Context) {
            val intent = Intent(context, LevelsActivity::class.java)
            context.startActivity(intent)
        }
    }
}