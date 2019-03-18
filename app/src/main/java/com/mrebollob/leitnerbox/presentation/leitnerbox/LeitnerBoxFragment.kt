package com.mrebollob.leitnerbox.presentation.leitnerbox

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.model.LeitnerDay
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.presentation.leitnerbox.adapter.LevelsAdapter

class LeitnerBoxFragment : Fragment(), LeitnerBoxView {

    //    val presenter: LeitnerBoxPresenter by inject()
    private var listener: LeitnerBoxFragmentListener? = null
    private val levelsAdapter = LevelsAdapter()
    private var currentDay = LeitnerDay.empty()

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
//        presenter.attachView(this)
    }

    private fun initUI() {
//        levelsListView.adapter = levelsAdapter
//        doneButton.setOnClickListener { presenter.onDayCompletedClick(LeitnerDay(currentDay.dayNumber)) }
    }

    override fun showFirstDayTitle() {
//        dayTextView.text = getString(R.string.leitner_view_first_day)
//        levelsTextView.text = getString(R.string.leitner_view_first_day_info)
    }

    override fun showCurrentNumberDay(day: LeitnerDay) {
        this.currentDay = day
//        dayTextView.text = getString(R.string.main_view_day_number, day.dayNumber)
    }

    override fun showLevelsToReview(levels: List<Level>) {
        if (levels.isEmpty()) {
            return
        }

//        val names = levels.filter { it.active }.map { it.name }.reversed()
//            .reduce { acc, string -> "$acc, $string" }
//        levelsTextView.text = getString(R.string.main_view_levels_to_review, names)
    }

    override fun showLevels(levels: List<Level>) {
        levelsAdapter.levels = levels
    }

    override fun onDayCompleted() {
        listener?.onDayCompleted()
    }

    override fun handleFailure(failure: Failure) {
        context?.toast(getString(R.string.generic_error))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LeitnerBoxFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement LeitnerBoxFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
//        presenter.detachView()
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
