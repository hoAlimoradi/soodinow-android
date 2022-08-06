package com.paya.presentation.utils.circularSeekBar

import android.R
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable


class ArrowEntity(private val centerPosition: PointF,
                  private var progress: Float,
                  private val startAngle: Float,
                  private val thumbRadius: Float,
                  private val arrowDrawable: ArrowDrawable) {

    companion object {
        private const val DEGREE_TO_RADIAN_RATIO = 0.0174533
    }

    init {
        updatePosition(progress)
    }

    fun draw(canvas: Canvas, progress: Float) {
        this.progress = progress
        val angle = updatePosition(progress)
        //canvas.rotate(angle)

        arrowDrawable.draw(canvas)
       /* val rotator = RotateDrawable()
        canvas.rotate(angle)

        rotator.drawable = arrowDrawable.mutate()
        rotator.draw(canvas)*/

    }

    private fun updatePosition(progress: Float): Float {
        val seekbarRadius = (Math.min(centerPosition.x, centerPosition.y) - thumbRadius /2) / 1.25
        //val seekbarRadius = (Math.min(centerPosition.x, centerPosition.y) - thumbRadius) / 2

        val angle = (startAngle + (360 - 2 * startAngle) * progress) * DEGREE_TO_RADIAN_RATIO

        val indicatorX = centerPosition.x - Math.sin(angle) * seekbarRadius
        val indicatorY = Math.cos(angle) * seekbarRadius + centerPosition.y

        arrowDrawable.setBounds(
            (indicatorX - thumbRadius).toInt(),
            (indicatorY - thumbRadius).toInt(),
            (indicatorX + thumbRadius).toInt(),
            (indicatorY + thumbRadius).toInt())
        arrowDrawable.setAngleValue(angle.toFloat())
        return angle.toFloat()

    }
}