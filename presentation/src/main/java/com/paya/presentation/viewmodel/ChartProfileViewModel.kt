package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ChartProfitRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.convertToPersianDate
import com.paya.presentation.utils.shared.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartProfileViewModel @Inject constructor(
	private val getChartProfitUseCase: UseCase<Long, List<ChartProfitRepoModel>>
) : BaseViewModel() {

	val chartProfit = MutableLiveData<Resource<List<ChartProfitRepoModel>>>()

	val chartListModels = mutableListOf<ChartListModel>()


	fun getChartProfit(
		boxId: Long
	) {
		chartProfit.value?.let {
			if (it.status == Status.LOADING)
				return
		}
		showLoading()
		viewModelScope.launch {
			val response = callResource(
				this@ChartProfileViewModel, getChartProfitUseCase.action(
					boxId
				)
			)
			if (response.status == Status.SUCCESS)
				response.data?.let {
					setCurrentBoxData(it)
				}
			chartProfit.postValue(response)
			hideLoading()
		}
	}

	private fun setCurrentBoxData(model: List<ChartProfitRepoModel>) {
		chartListModels.clear()

		model.forEach { chart ->
			val xListChart = mutableListOf<String>()
			val mainChartPoints = mutableListOf<Point>()
			chart.points.forEachIndexed { index, value ->
				val date = convertToPersianDate(value.date)
				xListChart.add("${date.persianMonth} / ${date.persianDay}")
				mainChartPoints.add(
					Point(
						index.toFloat(),
						value.price.toFloat(),
						0f,
						value.price.toLong()
					)
				)
			}
			chartListModels.add(ChartListModel(xListChart, mainChartPoints))
		}
	}
}

data class ChartListModel(
	val xListChart: MutableList<String>,
	val mainChartPoints: MutableList<Point>
)