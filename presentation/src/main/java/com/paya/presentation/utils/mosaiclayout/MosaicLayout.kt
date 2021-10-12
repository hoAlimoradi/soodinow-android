package com.paya.presentation.utils.mosaiclayout

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import kotlin.random.Random

class MosaicLayout : View {
    private var cornerRadius = 0f
    private var measuredWidth = 0f
    private var measuredHeight = 0f
    private val rect = RectF(0F, 0F, 0F, 0F)
    private val rectPath = Path()

    var percents: MutableList<Int> = mutableListOf(40, 30, 30)

    var cellViews: MutableList<CellView?> = mutableListOf()

    val colors = arrayOf(
        Color.parseColor("#FFFFFF"),
        Color.parseColor("#000000"),
        Color.parseColor("#FF8F00"),
        Color.parseColor("#EF6C00"),
        Color.parseColor("#D84315"),
        Color.parseColor("#37474F"),
        //...more
    )


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setWillNotDraw(false)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
        cornerRadius = 0.toFloat()
    }

    fun setPercentList(percentList: MutableList<Int>) {
        this.percents = percentList
        invalidate()
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        measuredWidth = (right - left).toFloat()
        measuredHeight = (bottom - top).toFloat()
        rect[0f, 0f, measuredWidth] = measuredHeight
        rectPath.reset()
        rectPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipPath(rectPath)
        onDrawRectangle(canvas)
    }

    private fun onDrawRectangle(canvas: Canvas) {

        areaCalculator(percents)

        cellViews.forEach{
            it?.let { cell ->
                cell.paint?.let { it1 ->
                    canvas.drawRect(
                        cell.left,
                        cell.top,
                        cell.right,
                        cell.bottom,
                        it1
                    )
                }
            }
        }
    }


    private fun areaCalculator(percents: MutableList<Int>) {

        val units: MutableList<Float?> = mutableListOf()

        percents.forEach {
            units.add( measuredWidth * it / 100f)
        }

        val unitsSize = units.size -1
        var cellViewTemp = CellView(left = 0f, top = 0F, right = 0F , bottom = 0F, itemCounter = 0)

        for (i in 0..unitsSize ) {

            when (i) {
                0 -> {
                    cellViewTemp = CellView(
                        left = 0f,
                        top = 0F,
                        right = units[i]!!,
                        bottom = measuredHeight,
                        itemCounter = 0)
                }

                unitsSize -> {
                    cellViewTemp = CellView(
                        left = cellViews[i-1]!!.right,
                        top = 0F,
                        right = measuredWidth,
                        bottom = measuredHeight,
                        itemCounter = unitsSize)
                }



                else -> {
                    cellViewTemp = CellView(
                        left = cellViews[i-1]!!.right ,
                        top = 0F,
                        right = units[i]!! + cellViews[i-1]!!.right,
                        bottom = measuredHeight,
                        itemCounter = i)
                }
            }
            cellViews.add(cellViewTemp)
        }

    }

}