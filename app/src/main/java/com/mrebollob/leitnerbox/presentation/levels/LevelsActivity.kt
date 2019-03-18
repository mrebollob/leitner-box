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
import com.mrebollob.leitnerbox.domain.model.Homework
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.presentation.levels.adapter.LevelsAdapter
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_levels.*
import java.util.*

class LevelsActivity : BaseActivity() {

    private lateinit var levelsViewModel: LevelsViewModel
    private val levelsAdapter = LevelsAdapter()
    private var currentDay = -1

    override fun layoutId() = R.layout.activity_levels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        levelsViewModel = viewModel(viewModelFactory) {
            observe(homework, ::handleHomework)
            failure(failure, ::handleError)
        }

        levelsListView.adapter = levelsAdapter

        doneButton.setOnClickListener {

            levelsViewModel.setCompletedDay(LeitnerDay(currentDay, Date()))
        }
        levelsViewModel.init()
    }

    private fun handleHomework(homework: Homework?) {
        homework ?: return

        currentDay = homework.day
        levelsAdapter.levels = homework.levels

        val names = homework.levels.map { it.name }
            .reduce { acc, string -> "$acc, $string" }
        levelsTextView.text = getString(R.string.main_view_levels_to_review, names)
        dayTextView.text = getString(R.string.main_view_day_number, homework.day)
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