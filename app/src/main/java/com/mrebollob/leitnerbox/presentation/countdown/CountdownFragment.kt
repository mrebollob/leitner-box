package com.mrebollob.leitnerbox.presentation.countdown

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.ONE_DAY_MILLIS
import com.mrebollob.leitnerbox.domain.extension.getCalendarForToday
import com.mrebollob.leitnerbox.domain.extension.gone
import com.mrebollob.leitnerbox.domain.extension.snack
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.extension.visible
import com.mrebollob.leitnerbox.domain.model.Hour
import kotlinx.android.synthetic.main.fragment_countdown.*
import org.koin.android.ext.android.inject


class CountdownFragment : Fragment(), CountdownView {

    val presenter: CountdownPresenter by inject()
    private var listener: CountdownListener? = null
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countdown, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    private fun initView() {
        showLeitnerButton.setOnClickListener { presenter.onShowLeitnerClick() }
    }

    override fun showStudyTimeCountdown(studyTime: Hour, addDay: Boolean) {

        studyTimeTextView.text =
            getString(R.string.countdown_view_study_time, studyTime.getString())

        var remainingTime = studyTime.getCalendarForToday().time.time - System.currentTimeMillis()

        if (addDay) {
            remainingTime += ONE_DAY_MILLIS
        }

        countDownTimer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if (!isAdded) {
                    return
                }

                val seconds = millisUntilFinished / 1000
                val hours = seconds / 3600
                val minutes = ((seconds - hours * 3600) / 60) + 1
                updateCircleProgressView(seconds.toInt())

                val res = resources
                hoursTextView?.apply {
                    text = res.getQuantityString(
                        R.plurals.hour_format,
                        hours.toInt(), hours
                    )
                }

                minutesTextView?.apply {
                    visible()
                    text = res.getQuantityString(
                        R.plurals.minute_format,
                        minutes.toInt(), minutes
                    )
                }
            }

            override fun onFinish() {
                updateCircleProgressView(0)

                hoursTextView?.apply {
                    text = getString(R.string.countdown_view_completed_time)
                }

                minutesTextView?.apply {
                    gone()
                }
            }
        }.start()
    }

    private fun updateCircleProgressView(secondsUntilFinished: Int) {
        val value = (86400 - secondsUntilFinished) / 24
        circleProgressView?.apply {
            currentValue = value
        }
    }

    override fun showAdvanceTimeError() {
        showLeitnerButton.snack(
            getString(
                R.string.countdown_view_advance_time_error,
                STUDY_HOUR_THRESHOLD
            )
        )
    }

    override fun goToLeitnerBoxScreen() {
        listener?.onGoToLeitnerBoxScreenClick()
    }

    override fun showLeitnerButtonEnabled() {
        context?.let {
            showLeitnerButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    it,
                    R.color.show_leitner_button_color_enabled
                )
            )
        }
    }

    override fun showLeitnerButtonDisabled() {
        context?.let {
            showLeitnerButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    it,
                    R.color.show_leitner_button_color_disabled
                )
            )
        }
    }

    override fun handleFailure(failure: Failure) {
        context?.toast(getString(R.string.generic_error))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CountdownListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CountdownListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
        countDownTimer?.cancel()
        listener = null
    }

    interface CountdownListener {
        fun onGoToLeitnerBoxScreenClick()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CountdownFragment()
    }
}
