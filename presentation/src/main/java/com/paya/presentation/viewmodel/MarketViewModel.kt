package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paya.presentation.utils.shared.Point

class MarketViewModel @ViewModelInject constructor(

) : ViewModel() {
	
	val pointsLiveData = MutableLiveData<List<Point>>()
	
	fun getPoints() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		pointsLiveData.value = points
	}
	
}