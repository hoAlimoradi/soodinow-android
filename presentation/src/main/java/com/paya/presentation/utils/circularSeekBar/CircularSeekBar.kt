package com.paya.presentation.utils.circularSeekBar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import com.paya.presentation.utils.getIranSans


//import com.paya.presentation.R


class CircularSeekBar : View {

    private companion object {
        private const val DEFAULT_START_ANGLE_DEG = 30f
        //private const val DEFAULT_THUMB_RADIUS_DP = 11
        private const val DEFAULT_THUMB_RADIUS_DP = 15
        private const val DEFAULT_TRACK_WIDTH_DP = 8
        private const val DEFAULT_BIG_CIRCLE_RADIUS_RADIUS_DP = 3 * DEFAULT_THUMB_RADIUS_DP
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.CircularSeekBar, 0, 0))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.CircularSeekBar, 0, 0))
    }

    private var thumbRadius = DEFAULT_THUMB_RADIUS_DP * resources.displayMetrics.density
    private var trackWidth = DEFAULT_TRACK_WIDTH_DP * resources.displayMetrics.density
    private var progressWidth = DEFAULT_TRACK_WIDTH_DP * resources.displayMetrics.density
    private var trackGradientArray: IntArray = context.resources.getIntArray(R.array.default_track_gradient)
    private var progressGradientArray = context.resources.getIntArray(R.array.default_index_gradient)
    private var progressGradientArrayPositions: FloatArray? = null
    private var thumbColor: Int = ContextCompat.getColor(context, R.color.default_thumb_color)
    private var startAngle = DEFAULT_START_ANGLE_DEG
    private var thumbDrawableId: Int = 0

    private var trackDrawable: TrackDrawable? = null
    private var progressDrawable: ProgressDrawable? = null
    private var thumbEntity: ThumbEntity? = null
    private var arrowEntity: ArrowEntity? = null



    private var showThumb: Boolean = true
    private var showProgress: Boolean = true

    private var progress: Float = 0f

    var interactive: Boolean = true

    var progressChangedCallback: (progress: Float) -> Unit = {}

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * Set whether thumb is visible. To update the view call invalidate().
     *
     * @param showThumb When set to false yhumb will not be drawn
     */
    fun setShowThumb(showThumb: Boolean) {
        this.showThumb = showThumb
    }

    /**
     * Set wheter progress bar is visible. To update the view call invalidate().
     *
     * @param showProgress When set to false progress bar will not be drawn
     */
    fun setShowProgress(showProgress: Boolean) {
        this.showProgress = showProgress
    }

    /**
     * @return boolean indicating whether progress bar is visible
     */
    fun getShowProgress() = showProgress

    /**
     * Set track width in dp. To update the view call invalidate().
     *
     * @param trackWidthDp Track width in dp
     */
    fun setTrackWidthDp(trackWidthDp: Int) {
        trackWidth = trackWidthDp * resources.displayMetrics.density
    }

    /**
     * Set track width in pixels. To update the view call invalidate().
     *
     * @param trackWidth Track width in pixels
     */
    fun setTrackWidth(trackWidth: Float) {
        this.trackWidth = trackWidth
    }

    /**
     * Set track width from dimension resource. To update the view call invalidate().
     *
     * @param widthDimensId Dimension resource id
     */
    fun setTrackWidth(@DimenRes widthDimensId: Int) {
        trackWidth = context.resources.getDimension(widthDimensId)
    }

    /**
     * Set progress width in dp. To update the view call invalidate().
     *
     * @param progressWidthDp Progress bar width in dp
     */
    fun setProgressWidthDp(progressWidthDp: Float) {
        this.progressWidth = progressWidthDp
    }

    /**
     * Set progress width in pixels. To update the view call invalidate().
     *
     * @param progressWidth Progress bar width in pixels
     */
    fun setProgressWidth(progressWidth: Float) {
        this.progressWidth = progressWidth
    }

    /**
     * Set progress width from dimension resource. To update the view call invalidate().
     *
     * @param progressWidthResourceId Progress bar width dimension resource
     */
    fun setProgressWidth(@DimenRes progressWidthResourceId: Int) {
        progressWidth = context.resources.getDimension(progressWidthResourceId)
    }

    /**
     * Set progress and invalidate the view
     *
     * @param progress Progress from 0.0 to 1.0
     */
    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        this.progress = when {
            progress in 0f..1f -> progress
            progress > 1f -> 1f
            else -> 0f
        }
        invalidate()
    }

    /**
     * @return Progress as float in range from 0.0 to 1.0
     */
    fun getProgress() = progress

    private fun applyAttributes(attributes: TypedArray) {
        try {
            startAngle = attributes.getFloat(R.styleable.CircularSeekBar_startAngleDegrees, startAngle)
            thumbRadius = attributes.getDimension(R.styleable.CircularSeekBar_thumbRadius, thumbRadius)
            thumbColor = attributes.getColor(R.styleable.CircularSeekBar_thumbColor, thumbColor)
            val trackGradientArrayId = attributes.getResourceId(R.styleable.CircularSeekBar_trackGradient, 0)
            if (trackGradientArrayId != 0) {
                trackGradientArray = resources.getIntArray(trackGradientArrayId)
            }

            val trackGradientArrayPositionsResourceId = attributes.getResourceId(R.styleable.CircularSeekBar_trackGradientPositions, 0)
            if (trackGradientArrayPositionsResourceId != 0) {
                val positionsIntArray = resources.getIntArray(trackGradientArrayPositionsResourceId)
                progressGradientArrayPositions = FloatArray(positionsIntArray.size) { positionsIntArray[it].div(100f) }
            }

            showThumb = attributes.getBoolean(R.styleable.CircularSeekBar_showThumb, showThumb)
            progressWidth = attributes.getDimension(R.styleable.CircularSeekBar_progressWidth, progressWidth)
            trackWidth = attributes.getDimension(R.styleable.CircularSeekBar_trackWidth, trackWidth)
            progress = attributes.getFloat(R.styleable.CircularSeekBar_progress, 0f)

            val gradientArrayResourceId = attributes.getResourceId(R.styleable.CircularSeekBar_progressGradient, 0)
            if (gradientArrayResourceId != 0) {
                progressGradientArray = resources.getIntArray(gradientArrayResourceId)
            }
            val gradientArrayPositionsResourceId = attributes.getResourceId(R.styleable.CircularSeekBar_progressGradientPositions, 0)
            if (gradientArrayPositionsResourceId != 0) {
                val positionsIntArray = resources.getIntArray(gradientArrayPositionsResourceId)
                progressGradientArrayPositions = FloatArray(positionsIntArray.size) { positionsIntArray[it].div(100f) }
            }

            interactive = attributes.getBoolean(R.styleable.CircularSeekBar_interactive, interactive)
            thumbDrawableId = attributes.getResourceId(R.styleable.CircularSeekBar_thumbDrawable, 0)
            showProgress = attributes.getBoolean(R.styleable.CircularSeekBar_showProgress, showProgress)
        } finally {
            attributes.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        init(measuredWidth / 2f, measuredHeight / 2f)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean =
            if (interactive) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        performClick()
                        handleMotionEvent(event)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        handleMotionEvent(event)
                    }
                }
                true
            } else {
                super.onTouchEvent(event)
            }

    private fun handleMotionEvent(event: MotionEvent) {
        val relativeX = measuredWidth / 2f - event.x
        val relativeY = event.y - measuredHeight / 2f
        val angle = Math.toDegrees(Math.atan2(relativeX.toDouble(), relativeY.toDouble()))
        setProgress(angleToProgress(if (angle > 0) angle else angle + 360f))
        progressChangedCallback.invoke(progress)
    }

    private fun angleToProgress(angle: Double): Float {
        val availableAngle = 360 - 2 * startAngle
        val relativeAngle = angle - startAngle
        return (relativeAngle / availableAngle).toFloat()
    }

    private fun init(centerX: Float, centerY: Float) {
        val centerPosition = PointF(centerX, centerY)
        val radiusPx = Math.min(centerX, centerY)
        val margin = Math.max(thumbRadius, trackWidth / 2f)
        trackDrawable = TrackDrawable(centerPosition, radiusPx, margin, trackGradientArray, startAngle, trackWidth)

        if (showProgress) {
            progressDrawable = ProgressDrawable(centerPosition, progress, radiusPx, margin, progressGradientArray, startAngle, progressWidth, progressGradientArrayPositions)
        }

        if (showThumb) {
            val thumbDrawable = if (thumbDrawableId != 0) ContextCompat.getDrawable(context, thumbDrawableId)!! else ThumbDrawable(thumbColor)
            thumbEntity = ThumbEntity(centerPosition, progress, startAngle, thumbRadius, thumbDrawable)
            //val test =  ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_drop_up_24)!!


            val arrowDrawable = ArrowDrawable(thumbColor)

            arrowEntity = ArrowEntity(centerPosition, progress, startAngle, thumbRadius, arrowDrawable)
        }



    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {


            //دایره بیرونی
            //val bigCircleRadius =  (measuredWidth ) / 2.3f  DEFAULT_THUMB_RADIUS_DP
            val bigCircleRadius =  ((measuredWidth ) / 2f ) - DEFAULT_BIG_CIRCLE_RADIUS_RADIUS_DP

            var radius = 40

            val  bigCirclePaint = Paint()
            bigCirclePaint.isAntiAlias = true
            bigCirclePaint.color = Color.rgb(246, 245, 250)
            bigCirclePaint.style = Paint.Style.FILL

            radius = bigCircleRadius.toInt()
            var oval = RectF(0f, 0f, width.toFloat() /2, height.toFloat()/2)
            oval = RectF(
                (canvas.width / 2 - radius).toFloat(),
                (canvas.height / 2 - radius).toFloat(),
                (canvas.width / 2 + radius).toFloat(),
                (canvas.height / 2 + radius).toFloat()
            )
            this.drawArc(oval, -220f ,260f , true, bigCirclePaint)

            //دایره درونی
            val smallCircleRadius =  measuredWidth / 3f
            val smallCircleShadowRadius =  measuredWidth / 4f
            val paint = Paint().apply {
                color = Color.WHITE
                //style = Paint.Style.STROKE
                style = Paint.Style.FILL
                strokeWidth = 10f
                //سایه
                setShadowLayer(smallCircleShadowRadius, 0f, smallCircleShadowRadius, ContextCompat.getColor(context, R.color.aluminium))
            }
            this.drawCircle(measuredWidth / 2f, measuredHeight / 2f, smallCircleRadius, paint)

            val dashCircleRadius =  measuredWidth / 4f
            val dashPath = DashPathEffect(floatArrayOf(25f, 1f), 155.0.toFloat())
            val paintDash = Paint().apply {
                color = ContextCompat.getColor(context, R.color.alto_light)
                //style = Paint.Style.STROKE
                style = Paint.Style.FILL
                strokeWidth = 10f
            }
            paintDash.pathEffect = dashPath
            paintDash.style = Paint.Style.STROKE
            this.drawCircle(measuredWidth / 2f, measuredHeight / 2f, dashCircleRadius, paintDash)


            // مقدار
            paint.color = ContextCompat.getColor(context, R.color.emperor)
            paint.textSize = 40f
            paint.textAlign = Paint.Align.CENTER
            paint.typeface = getIranSans(context)
            //this.drawText("Some Text", 10f, 30f, paint)
            this.drawText("میزان ریسک شما",   (measuredWidth / 2f ) - 10 ,  measuredHeight / 2f + 50, paint)

            paint.color = ContextCompat.getColor(context, R.color.light_purple7d4181)
            paint.textSize = 40f
            paint.textAlign = Paint.Align.CENTER
            //this.drawText("Some Text", 10f, 30f, paint)
            this.drawText("0" ,   (measuredWidth / 2f ) - 10 ,  measuredHeight / 2f  - 40, paint)


            // پراگرس
            trackDrawable?.draw(this)
            progressDrawable?.draw(this, progress)
            thumbEntity?.draw(canvas, progress)
            //arrowEntity?.draw(canvas, progress)

        }
    }
}
