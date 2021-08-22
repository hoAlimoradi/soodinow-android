package com.paya.presentation.viewmodel

import android.graphics.Color
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.home.fragments.CardAccount
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.enum.FilterProfile
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val getBoxHistoryUseCase: UseCase<BoxHistoryRequestModel, BoxHistoryRepoModel>,
	private val existAccountUseCase: UseCase<Unit, ExitAccountRepoModel>
) : BaseViewModel() {


	val pieChartStatus = MutableLiveData<PieChartModel>()
	private var pieChartModel: PieChartModel? = null
	private var profile = MutableLiveData<Resource<BoxHistoryRepoModel>>()
	val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
	val errorMessage = MutableLiveData<String>()
	val cardAccounts = mutableListOf<CardAccount>()

	var currentBoxId: Long = 0


	fun setErrorMessage(message: String) {
		errorMessage.value = message
	}

	fun getExistAccount() {
		showLoading()
		viewModelScope.launch {
val resource = callResource(this@ProfileViewModel, existAccountUseCase.action(Unit))
			if (resource.status == Status.SUCCESS) {
				cardAccounts.clear()
				resource.data?.activeBox?.let {
					it.forEach { activeBox ->
						cardAccounts.add(CardAccount.newInstance(activeBox))
					}

					if (it.isEmpty()) {
						setErrorMessage("شما حساب فعال ندارید \n" +
								"ابتدا پیمان خود را با ما ببندید")
						hideLoading()
					} else {
						currentBoxId = it.first().id
						getProfile(
							it.first().id
						)
					}
				}
			} else  {
				resource.message?.let { setErrorMessage(it) }
				hideLoading()
			}
			hideLoading()
			existAccount.postValue(resource)
		}
	}

	fun getProfile(
		boxId: Long
	) {

		profile.value?.let {
			if (it.status == Status.LOADING)
				return
		}
		profile.value = Resource.loading(null)
		viewModelScope.launch {
			showLoading()
			val response = callResource(
				this@ProfileViewModel, getBoxHistoryUseCase.action(
					BoxHistoryRequestModel(boxId, FilterProfile.week.name, 1)
				)
			)
			response.data?.circleChart?.let {
				fillEntries(it)
			}
			hideLoading()
		}

	}

	private fun fillEntries(chartData: List<CircleChartDataRepoModel>) {
		viewModelScope.launch(Dispatchers.Main) {
			if (pieChartModel != null)
				return@launch
			pieChartModel = PieChartModel()
			pieChartModel?.let { pieChartModel ->
				if (chartData.isNotEmpty() && pieChartModel.chartLabels.isEmpty() && pieChartModel.entries.isEmpty()) {
					var allSize: Long = 0
					chartData.forEach {
						allSize += it.quantity
					}
					chartData.forEachIndexed { _, pieChartData ->
						if (pieChartModel.entries.size <= chartData.size)
							pieChartModel.entries.add(PieEntry(((pieChartData.quantity * 100) / allSize).toFloat()))
						if (pieChartModel.chartColor.size <= chartData.size)
							pieChartModel.chartColor.add(Color.parseColor(pieChartData.color))
						if (pieChartModel.chartLabels.size <= chartData.size)
							pieChartModel.chartLabels.add(
								ChartLabelAdapter.ChartLabelModel(
									pieChartData.name,
									pieChartData.color
								)
							)
					}
					pieChartStatus.value = pieChartModel
				}
			}
		}
	}


}

