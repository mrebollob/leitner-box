package com.mrebollob.leitnerbox.presentation.countdown

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.model.Hour
import com.mrebollob.leitnerbox.util.extensions.getCalendarForToday
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
        presenter.attachView(this)
    }

    private fun initView() {
        skipButton.setOnClickListener { presenter.onSkipButtonClick() }
    }

    override fun showStudyTime(studyTime: Hour) {
        StudyTimeTextView.text =
                getString(R.string.countdown_view_study_time, studyTime.getString())
    }

    override fun showCountdown(studyTime: Hour) {
        val remainingTime = studyTime.getCalendarForToday().time.time - System.currentTimeMillis()

        countDownTimer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val seconds = millisUntilFinished / 1000
                val hours = seconds / 3600
                val minutes = ((seconds - hours * 3600) / 60) + 1
                updateCircleProgressView(seconds.toInt())

                val res = resources
                hoursTextView.text = res.getQuantityString(
                    R.plurals.hour_format,
                    hours.toInt(), hours
                )
                minutesTextView.text = res.getQuantityString(
                    R.plurals.minute_format,
                    minutes.toInt(), minutes
                )
            }

            override fun onFinish() {
                hoursTextView.text = getString(R.string.countdown_view_completed_time)
                minutesTextView.text = ""
            }
        }.start()
    }

    private fun updateCircleProgressView(secondsUntilFinished: Int) {
        val value = (86400 - secondsUntilFinished) / 24
        circleProgressView.currentValue = value
    }

    override fun goToLeitnerBoxScreen() {
        listener?.onGoToLeitnerBoxScreenClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CountdownListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement CountdownListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
        listener = null
        countDownTimer?.cancel()
    }

    interface CountdownListener {
        fun onGoToLeitnerBoxScreenClick()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CountdownFragment()
    }
}
