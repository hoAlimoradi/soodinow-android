package com.paya.presentation.utils.circularSeekBar

import android.graphics.*
import android.graphics.drawable.Drawable


class ArrowDrawable(thumbColor: Int) : Drawable() {
    var angle: Float = 0F
    private val whitePaint = Paint().apply {
        color = Color.WHITE
        alpha = 255
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val thumbOuterPaint = Paint().apply {
        isAntiAlias = true
        color = thumbColor
        alpha = 102
    }

    private val thumbInnerPaint = Paint().apply {
        isAntiAlias = true
        color = thumbColor
    }

    /*private val thumbInnerPaint = Paint().apply {
        isAntiAlias = true
        color = thumbColor
    }*/

    fun setAngleValue(angleVAlue: Float) {
        angle = angleVAlue
    }
    override fun draw(canvas: Canvas) {
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()
        val radius = centerX - bounds.left

        canvas.apply {
            //drawCircle(centerX, centerY, radius, thumbOuterPaint)
            //drawCircle(centerX, centerY, radius / 2f, thumbInnerPaint)

           // drawCircle(centerX, centerY, 3f, whitePaint)


            //drawCircle(centerX, centerY, radius , thumbInnerPaint)
            val point1Draw = Point(centerX.toInt() + 15, centerY.toInt() + 0)
            val point2Draw = Point(centerX.toInt() + 0, centerY.toInt() + 36)
            val point3Draw = Point(centerX.toInt() + 36, centerY.toInt() + 36)
            val path = Path()
            path.moveTo(point1Draw.x.toFloat(), point1Draw.y.toFloat())
            path.lineTo(point2Draw.x.toFloat(), point2Draw.y.toFloat())
            path.lineTo(point3Draw.x.toFloat(), point3Draw.y.toFloat())
            path.lineTo(point1Draw.x.toFloat(), point1Draw.y.toFloat())
            path.close()
            canvas.drawARGB(0, 0, 0, 0)
            //thumbInnerPaint.color = Color.parseColor("#BAB399")
            canvas.drawPath(path, thumbInnerPaint)
            canvas.rotate(angle)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    fun getTriangleBitmap(bitmap: Bitmap, radius: Int): Bitmap? {
        val finalBitmap: Bitmap = if (bitmap.width != radius || bitmap.height != radius) Bitmap.createScaledBitmap(
            bitmap, radius, radius,
            false
        ) else bitmap
        val output = Bitmap.createBitmap(
            finalBitmap.width,
            finalBitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(
            0, 0, finalBitmap.width,
            finalBitmap.height
        )
        val point1Draw = Point(75, 0)
        val point2Draw = Point(0, 180)
        val point3Draw = Point(180, 180)
        val path = Path()
        path.moveTo(point1Draw.x.toFloat(), point1Draw.y.toFloat())
        path.lineTo(point2Draw.x.toFloat(), point2Draw.y.toFloat())
        path.lineTo(point3Draw.x.toFloat(), point3Draw.y.toFloat())
        path.lineTo(point1Draw.x.toFloat(), point1Draw.y.toFloat())
        path.close()
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.parseColor("#BAB399")
        canvas.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(finalBitmap, rect, rect, paint)
        return output
    }
}
