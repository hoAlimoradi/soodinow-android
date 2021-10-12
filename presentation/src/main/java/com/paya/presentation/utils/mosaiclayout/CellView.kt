package com.paya.presentation.utils.mosaiclayout

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

data class CellView(val left: Float, val top: Float, val right: Float, val bottom: Float, val itemCounter: Int) {
    val colors = arrayOf(
        Color.parseColor("#FF8F00"),
        Color.parseColor("#33FFF0"),
        Color.parseColor("#2AACF2"),
        Color.parseColor("#37474F"),
        Color.parseColor("#6099B7"),
        Color.parseColor("#EF6C00"),
        Color.parseColor("#D84315"),
        Color.parseColor("#F468AA")
        //...more
    )

    var paint: Paint? = null

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint?.let {
            it.color = colors[itemCounter]
            it.style = Paint.Style.FILL
        }
    }


    private fun drawText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        paint: TextPaint,
        aligment: Layout.Alignment
    ) {
        canvas.save()
        run {
            canvas.translate(x, y)
            val staticLayout = StaticLayout(
                text, paint,
                paint.measureText(text).toInt(), aligment, 1.0f, 0f, false
            )
            staticLayout.draw(canvas)
        }
        canvas.restore()
    }
}

