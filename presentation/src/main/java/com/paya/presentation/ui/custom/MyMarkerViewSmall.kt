package com.paya.presentation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Path.FillType
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.paya.presentation.R
import com.paya.presentation.utils.shared.EntryPoint


@SuppressLint("ViewConstructor")
class MyMarkerViewSmall(context: Context,private val parentView: View) :
	MarkerView(context,R.layout.popup_linear_chart_small) {
	
	private var posY: Int? = null
	private var posX: Int? = null
	private var type = 0
	private val percentTextView: TextView = findViewById(R.id.percent)
	
	@SuppressLint("SetTextI18n")
	override fun refreshContent(e: Entry,highlight: Highlight?) {
		if (e is EntryPoint) {
			percentTextView.text = (if (e.percent == 0.0f) Utils.formatNumber(
				e.y,
				0,
				true
			) else e.percent.toString()) + " %"
		} else if (e is CandleEntry) {
			percentTextView.text = Utils.formatNumber(e.high,0,true) + " %"
		} else {
			percentTextView.text = Utils.formatNumber(e.y,0,true) + " %"
		}
		
		super.refreshContent(e,highlight)
	}
	
	override fun getOffset(): MPPointF? {
		val lineChartWidth = parentView.width
		val lineChartHeight = parentView.height
		var x = -(width / 2)
		var y = -height
		type = 1
		if (posX!! + width > lineChartWidth) {
			val over = if((lineChartWidth - posX!!)==0) 1 else (lineChartWidth - posX!!)
			x = -(width - over)
			
		}
		if (height + posY!! < lineChartHeight) {
			y = 5
			type = 0
		}
		
		return MPPointF(x.toFloat(),y.toFloat())
	}
	
	override fun getOffsetForDrawingAtPoint(posX: Float,posY: Float): MPPointF {
		this.posX = posX.toInt()
		this.posY = posY.toInt()
		return super.getOffsetForDrawingAtPoint(posX,posY)
	}
	
	@SuppressLint("DrawAllocation")
	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)

		val paint = Paint()
		paint.color = Color.WHITE
		paint.style = Paint.Style.FILL_AND_STROKE
		paint.isAntiAlias = true
		
		
		val path = Path()
		path.addRect(0f, 11f, width.toFloat(), height.toFloat() -13f, Path.Direction.CW)
		val path1 = Path()
		
		path1.moveTo(0F, if(type == 1) 0f else 15f );
		path1.lineTo(6f, if(type == 0) 0f else 15f);
		path1.lineTo(15f, if(type == 1) 0f else 15f);
		path1.fillType = FillType.EVEN_ODD
		path1.close()
		var dx = (offset!!.x * -1) -6
		if (width / 2 >= posX!!) {
			dx = posX!!.toFloat() -6
		}
		path.addPath(path1,dx,if(type == 0) -4f else height.toFloat()-13f)
		path.close()
		
		canvas!!.drawPath(path,paint)
	}
	
}