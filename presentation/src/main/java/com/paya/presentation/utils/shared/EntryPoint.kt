package com.paya.presentation.utils.shared

import com.github.mikephil.charting.data.Entry

data class EntryPoint(val xValue: Float, val yValue:Float, val percent:Float = 0.0f, val price:Long = 0) : Entry(xValue,yValue)