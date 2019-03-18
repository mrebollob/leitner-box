package com.mrebollob.leitnerbox.presentation.main.countdown

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.failure
import com.mrebollob.leitnerbox.domain.extension.gone
import com.mrebollob.leitnerbox.domain.extension.observe
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.domain.extension.viewModel
import com.mrebollob.leitnerbox.domain.extension.visible
import com.mrebollob.leitnerbox.presentation.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_countdown.*
import java.util.*


class CountdownFragment : BaseFragment() {

    lateinit var countdownViewModel: CountdownViewModel
    override fun layoutId(): Int = R.layout.fragment_countdown
    private var timer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countdownViewModel = viewModel(viewModelFactory) {
            observe(studyTime, ::handleStudyTime)
            failure(failure, ::handleError)
        }

        countdownViewModel.init()
    }

    private fun handleStudyTime(studyTime: Date?) {
        studyTime ?: return

//        studyTimeTextView.text =
//            getString(R.string.countdown_view_study_time, studyTime.getString())

        var remainingTime = studyTime.time - System.currentTimeMillis()

        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if (!isAdded) {
                    return
                }

                val seconds = millisUntilFinished / 1000
                val hours = seconds / 3600
                val minutes = ((seconds - hours * 3600) / 60) + 1
                updateCircleProgressView(seconds.toInt())

                val res = resources
//                hoursTextView?.apply {
//                    text = res.getQuantityString(
//                        R.plurals.hour_format,
//                        hours.toInt(), hours
//                    )
//                }
//
//                minutesTextView?.apply {
//                    visible()
//                    text = res.getQuantityString(
//                        R.plurals.minute_format,
//                        minutes.toInt(), minutes
//                    )
//                }
            }

            override fun onFinish() {
                updateCircleProgressView(0)

//                hoursTextView?.apply {
//                    text = getString(R.string.countdown_view_completed_time)
//                }
//
//                minutesTextView?.apply {
//                    gone()
//                }
            }
        }.start()
    }

    private fun updateCountDownView(millisUntilFinished: Long) {

    }

    private fun showFirstDay() {
        countdownView.gone()
        firstDayView.visible()
        setLeitnerButtonEnabled()
    }

    private fun setLeitnerButtonEnabled() {
        context?.let {
            showLeitnerButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    it,
                    R.color.show_leitner_button_color_enabled
                )
            )
        }

        showLeitnerButton.setOnClickListener {
            context?.toast("Hola")
        }
    }

    private fun setLeitnerButtonDisabled() {
        context?.let {
            showLeitnerButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    it,
                    R.color.show_leitner_button_color_disabled
                )
            )
        }
    }

    private fun updateCircleProgressView(secondsUntilFinished: Int) {
        val value = (86400 - secondsUntilFinished) / 24
//        circleProgressView?.apply {
//            currentValue = value
//        }
    }

    private fun handleError(failure: Failure?) {
        when (failure) {
            Failure.EmptyData -> showFirstDay()
            else -> context?.toast(getString(R.string.generic_error))
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CountdownFragment()
    }
}
