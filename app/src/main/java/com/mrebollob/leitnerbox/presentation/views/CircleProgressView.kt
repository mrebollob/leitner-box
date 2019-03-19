package com.mrebollob.leitnerbox.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.mrebollob.leitnerbox.R

import timber.log.Timber

// From https://github.com/jiahuanyu/CircleTimerView

class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mCirclePaint: Paint? = null
    private var mHighlightLinePaint: Paint? = null
    private var mLinePaint: Paint? = null

    private var mGapBetweenCircleAndLine: Float = 0.toFloat()
    private var mLineLength: Float = 0.toFloat()
    private var mLongerLineLength: Float = 0.toFloat()
    private var mCircleButtonRadius: Float = 0.toFloat()
    private var mCircleStrokeWidth: Float = 0.toFloat()

    private var mCx: Float = 0.toFloat()
    private var mCy: Float = 0.toFloat()
    private var mRadius: Float = 0.toFloat()
    private var mCurrentRadian: Float = 0.toFloat()
    private var mCurrentValue: Int = 0

    var currentValue: Int
        get() = mCurrentValue
        set(currentValue) {
            if (currentValue in 0..3600) {
                mCurrentValue = currentValue
                this.mCurrentRadian =
                    ((currentValue / 60.0f).toDouble() * 2.0 * Math.PI / 60).toFloat()
                invalidate()
            }
        }

    init {
        initialize()
    }

    private fun initialize() {
        mGapBetweenCircleAndLine = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_GAP_BETWEEN_CIRCLE_AND_LINE,
            context.resources.displayMetrics
        )
        mLineLength = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LINE_LENGTH, context.resources
                .displayMetrics
        )
        mLongerLineLength = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LONGER_LINE_LENGTH, context
                .resources.displayMetrics
        )
        val mLineWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LINE_WIDTH, context.resources
                .displayMetrics
        )
        mCircleButtonRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_CIRCLE_BUTTON_RADIUS, context
                .resources.displayMetrics
        )
        mCircleStrokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_CIRCLE_STROKE_WIDTH, context
                .resources.displayMetrics
        )

        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mHighlightLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mCirclePaint!!.color = ContextCompat.getColor(context, R.color.countdown_circle_color)
        mCirclePaint!!.style = Paint.Style.STROKE
        mCirclePaint!!.strokeWidth = mCircleStrokeWidth

        mLinePaint!!.color = ContextCompat.getColor(context, R.color.countdown_line_color)
        mLinePaint!!.strokeWidth = mLineWidth

        mHighlightLinePaint!!.color =
            ContextCompat.getColor(context, R.color.countdown_highlight_color)
        mHighlightLinePaint!!.strokeWidth = mLineWidth
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(mCx, mCy, mRadius, mCirclePaint!!)
        canvas.save()
        for (i in 0..119) {
            canvas.save()
            canvas.rotate((360 / 120 * i).toFloat(), mCx, mCy)
            if (i % 30 == 0) {
                if (360 / 120 * i <= Math.toDegrees(mCurrentRadian.toDouble())) {
                    canvas.drawLine(
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine,
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine +
                                mLongerLineLength,
                        mHighlightLinePaint!!
                    )
                } else {
                    canvas.drawLine(
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine,
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine +
                                mLongerLineLength,
                        mLinePaint!!
                    )
                }
            } else {
                if (360 / 120 * i <= Math.toDegrees(mCurrentRadian.toDouble())) {
                    canvas.drawLine(
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine,
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine + mLineLength,
                        mHighlightLinePaint!!
                    )
                } else {
                    canvas.drawLine(
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine,
                        mCx,
                        measuredHeight / 2 - mRadius + mCircleStrokeWidth / 2 + mGapBetweenCircleAndLine + mLineLength,
                        mLinePaint!!
                    )
                }
            }
            canvas.restore()
        }
        canvas.restore()

        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        this.mCx = (width / 2).toFloat()
        this.mCy = (height / 2).toFloat()

        if (mLineLength / 2 + mGapBetweenCircleAndLine + mCircleStrokeWidth >= mCircleButtonRadius) {
            this.mRadius = width / 2 - mCircleStrokeWidth / 2
            Timber.d("No exceed")
        } else {
            this.mRadius = width / 2 -
                    (mCircleButtonRadius - mGapBetweenCircleAndLine - mLineLength / 2 -
                            mCircleStrokeWidth / 2)
            Timber.d("Exceed")
        }
        setMeasuredDimension(width, height)
    }

    override fun onSaveInstanceState(): Parcelable? {
        Timber.d("onSaveInstanceState")
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState())
        bundle.putFloat(STATUS_RADIAN, mCurrentRadian)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        Timber.d("onRestoreInstanceState")
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(INSTANCE_STATUS))
            mCurrentRadian = state.getFloat(STATUS_RADIAN)
            mCurrentValue = (60 / (2 * Math.PI) * mCurrentRadian.toDouble() * 60.0).toInt()
            return
        }
        super.onRestoreInstanceState(state)
    }

    companion object {

        private const val INSTANCE_STATUS = "instance_status"
        private const val STATUS_RADIAN = "status_radian"

        private const val DEFAULT_GAP_BETWEEN_CIRCLE_AND_LINE = 5f
        private const val DEFAULT_LINE_LENGTH = 14f
        private const val DEFAULT_LONGER_LINE_LENGTH = 23f
        private const val DEFAULT_LINE_WIDTH = 0.5f
        private const val DEFAULT_CIRCLE_BUTTON_RADIUS = 15f
        private const val DEFAULT_CIRCLE_STROKE_WIDTH = 1f
    }
}