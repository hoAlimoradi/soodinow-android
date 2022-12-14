package com.paya.presentation.viewmodel

import android.graphics.Color
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CreateLowRiskAccountViewModel @Inject constructor(
	private val lowRiskInvestmentRepository: LowRiskInvestmentRepository,
	private val getChartProfitUseCase: UseCase<Long, List<ChartProfitRepoModel>>
) : BaseViewModel() {

	val chartProfit = MutableLiveData<Resource<List<ChartProfitRepoModel>>>()
	val farabiWalletRepoModelResource = MutableLiveData<Resource<List<SoodinowWalletContractRepoModel>>>()
	val soodinowWalletRepoModelResource = MutableLiveData<Resource<List<SoodinowWalletContractRepoModel>>>()
	val lowRiskResource = MutableLiveData<Resource<IsInRiskListRepoModel>>()
	val pieChartStatus = MutableLiveData<PieChartModel>()
	private var pieChartModel: PieChartModel? = null

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
				this@CreateLowRiskAccountViewModel, getChartProfitUseCase.action(
					boxId
				)
			)
			if (response.status == Status.SUCCESS)
				/*response.data?.let {
					setCurrentBoxData(it)
				}*/
			chartProfit.postValue(response)
			hideLoading()
		}
	}

	fun getLowRiskStocks(type: String, price: Long) {
		showLoading()
		viewModelScope.launch {
			val response = callResource(
				this@CreateLowRiskAccountViewModel, lowRiskInvestmentRepository.getLowRiskStocks(
					LowRiskStockRequest(type, price)
				)
			)
			if (response.status == Status.SUCCESS) {
				pieChartModel = null
				response.data?.let { fillEntries(it.basket) }
			}
			lowRiskResource.postValue(response)
			hideLoading()
		}

	}

	private fun fillEntries(basket: List<BasketRepoModel>) {
		viewModelScope.launch(Dispatchers.Main) {

			if (pieChartModel != null)
				return@launch
			pieChartModel = PieChartModel()
			pieChartModel?.let { pieChartModel ->
				if (basket.isNotEmpty() && pieChartModel.chartLabels.isEmpty() && pieChartModel.entries.isEmpty()) {
					var allSize: Float = 0f
					var totalAmount: Float = 0f
					basket.forEach {
						allSize += it.quantity
					}

					basket.forEach {
						totalAmount += (it.quantity * it.price)
					}
					basket.forEachIndexed { _, pieChartData ->
						if (pieChartModel.entries.size <= basket.size) {
							pieChartModel.entries.add(PieEntry(((pieChartData.quantity * pieChartData.price) / totalAmount).toFloat()))
						}

						if (pieChartModel.chartColor.size <= basket.size && pieChartData.color.isNotEmpty())
							pieChartModel.chartColor.add(Color.parseColor(pieChartData.color))
						if (pieChartModel.chartLabels.size <= basket.size)
							pieChartModel.chartLabels.add(
								ChartLabelAdapter.ChartLabelModel(
									pieChartData.namad,
									pieChartData.color
								)
							)
					}
					pieChartStatus.value = pieChartModel
				}
			}
		}
	}


	fun getSoodinowWalletContracts() {

		viewModelScope.launch {
			val response = callResource(
				this@CreateLowRiskAccountViewModel, lowRiskInvestmentRepository.getSoodinowWalletContractRepoModel()
			)
			soodinowWalletRepoModelResource.postValue(response)
		}
	}

	fun getFarabiWalletContracts() {

		viewModelScope.launch {
			val response = callResource(
				this@CreateLowRiskAccountViewModel, lowRiskInvestmentRepository.getSoodinowWalletContractRepoModel()
			)
			farabiWalletRepoModelResource.postValue(response)
		}
	}
}