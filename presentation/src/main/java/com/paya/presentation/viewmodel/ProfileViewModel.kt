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

	val profile = MutableLiveData<Resource<MutableList<EfficiencyRepoModel>>>()
	val pieChartStatus = MutableLiveData<PieChartModel>()
	private var pieChartModel: PieChartModel? = null
	private var profileDay: Resource<BoxHistoryRepoModel>? = null
	private var profileMonth: Resource<BoxHistoryRepoModel>? = null
	private var profileWeek: Resource<BoxHistoryRepoModel>? = null
	private val efficiencyList: MutableList<EfficiencyRepoModel> = mutableListOf()
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
			existAccount.postValue(resource)
		}
	}

	fun getProfile(
		boxId: Long
	) {
		efficiencyList.clear()
		pieChartModel = null
		profileWeek = null
		profileDay = null
		profileMonth = null
		profile.value?.let {
			if (it.status == Status.LOADING)
				return
		}
		profile.value = Resource.loading(null)
		showLoading()
		getProfileWeek(boxId)
		getProfileDay(boxId)
		getProfileMonth(boxId)
	}


	private fun getProfileWeek(
		boxId: Long
	) {
		viewModelScope.launch {
			val response = callResource(
				this@ProfileViewModel, getBoxHistoryUseCase.action(
					BoxHistoryRequestModel(boxId, FilterProfile.week.name, 1)
				)
			)
			if (response.status == Status.SUCCESS) {
				response.data?.efficiency?.let {
					it.title = "بازدهی هفتگی"
					it.position = 1
					efficiencyList.add(it)
				}
			}
			profileWeek = response
			Log.d("getProfileWeek", "getProfileWeek")
			endProfile(response)
		}
	}

	private fun getProfileDay(
		boxId: Long
	) {
		viewModelScope.launch {
			val response = callResource(
				this@ProfileViewModel, getBoxHistoryUseCase.action(
					BoxHistoryRequestModel(boxId, FilterProfile.day.name, 3)
				)
			)
			if (response.status == Status.SUCCESS) {
				response.data?.efficiency?.let {
					it.title = "بازدهی روزانه"
					it.position = 2
					efficiencyList.add(it)
				}
			}
			profileDay = response
			Log.d("getProfileDay", "getProfileDay")
			endProfile(response)
		}
	}

	private fun getProfileMonth(
		boxId: Long
	) {
		viewModelScope.launch {
			val response = callResource(
				this@ProfileViewModel, getBoxHistoryUseCase.action(
					BoxHistoryRequestModel(boxId, FilterProfile.month.name, 3)
				)
			)
			if (response.status == Status.SUCCESS) {
				response.data?.efficiency?.let {
					it.title = "بازدهی ماهانه"
					it.position = 0
					efficiencyList.add(it)
				}
			}
			profileMonth = response
			Log.d("getProfileMonth", "getProfileMonth")
			endProfile(response)
		}
	}


	private fun endProfile(resource: Resource<BoxHistoryRepoModel>) {
		viewModelScope.launch(Dispatchers.Main) {
			if (resource.status == Status.SUCCESS) {
				resource.data?.circleChart?.let {
					fillEntries(it)
				}

			}
			if (resource.status == Status.ERROR)
				profile.postValue(resource.message?.let { Resource.error(it, null) })
			if (profileDay != null && profileMonth != null && profileWeek != null && efficiencyList.size == 3) {
				efficiencyList.sortedBy { it.position }
				profile.value?.let {
					if (it.status == Status.SUCCESS)
						return@launch
				}
				Log.d("endProfile", efficiencyList.size.toString())
				profile.postValue(Resource.success(efficiencyList))
				hideLoading()
			}
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

