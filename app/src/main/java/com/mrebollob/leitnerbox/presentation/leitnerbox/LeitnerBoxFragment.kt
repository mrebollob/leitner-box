package com.mrebollob.leitnerbox.presentation.leitnerbox

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.leitnerbox.adapter.LevelsAdapter
import kotlinx.android.synthetic.main.fragment_leitner_box.*
import org.koin.android.ext.android.inject

class LeitnerBoxFragment : Fragment(), LeitnerBoxView {

    val presenter: LeitnerBoxPresenter by inject()
    private var listener: LeitnerBoxFragmentListener? = null
    private val levelsAdapter = LevelsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leitner_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    private fun initUI() {
        levelsListView.adapter = levelsAdapter
        dayDoneButton.setOnClickListener { presenter.onDayCompletedClick() }
    }

    override fun showCurrentNumberDay(currentDay: Int) {
        dayTextView.text = getString(R.string.main_view_day_number, currentDay)
    }

    override fun showLevels(levels: List<Level>) {
        levelsAdapter.levels = levels

        if (levels.isEmpty()) {
            return
        }

        val names = levels.filter { it.active }.map { it.name }.reversed()
            .reduce { acc, string -> "$acc, $string" }
        levelsTextView.text = getString(R.string.main_view_levels_to_review, names)
    }

    override fun showDayDone() {
        dayDoneButton.text = getString(R.string.leitner_view_day_done_button)
    }

    override fun showDayToDo() {
        dayDoneButton.text = getString(R.string.leitner_view_mark_as_done_button)
    }

    override fun onDayCompleted() {
        listener?.onDayCompleted()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LeitnerBoxFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement LeitnerBoxFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
        listener = null
    }

    interface LeitnerBoxFragmentListener {
        fun onDayCompleted()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LeitnerBoxFragment()
    }
}
