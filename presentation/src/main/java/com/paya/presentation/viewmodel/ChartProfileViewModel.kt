package com.paya.presentation.viewmodel

import android.graphics.Color
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.ui.profile.enum.FilterProfile
import com.paya.presentation.utils.*
import com.paya.presentation.utils.shared.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
@HiltViewModel
class ChartProfileViewModel @Inject constructor(
	private val getBoxHistoryUseCase: UseCase<BoxHistoryRequestModel, BoxHistoryRepoModel>
) : BaseViewModel() {

	val profile = MutableLiveData<Resource<BoxHistoryRepoModel>>()
	var number = 3
	var filterProfile = FilterProfile.day
	var xListChart = mutableListOf<String>()
	val mainChartPoints = mutableListOf<Point>()


	fun getProfile(
		boxId: Long
	) {
		profile.value?.let {
			if (it.status == Status.LOADING)
				return
		}
		profile.value = Resource.loading(null)
		viewModelScope.launch {
			val response = callResource(
				this@ChartProfileViewModel, getBoxHistoryUseCase.action(
					BoxHistoryRequestModel(boxId, filterProfile.name, number)
				)
			)
			if (response.status == Status.SUCCESS)
				response.data?.let {
					setCurrentBoxData(it)
				}
			profile.postValue(response)
		}
	}

	private fun setCurrentBoxData(boxModel: BoxHistoryRepoModel) {
		xListChart.clear()
		mainChartPoints.clear()
		boxModel.mainChart.data.forEachIndexed { index, value ->
			val date = convertToPersianDate(value.date)
			xListChart.add("${date.persianMonth} / ${date.persianDay}")
			mainChartPoints.add(
				Point(
					index.toFloat(),
					value.price.toFloat(),
					boxModel.percent,
					value.price.toLong()
				)
			)
		}
	}
}