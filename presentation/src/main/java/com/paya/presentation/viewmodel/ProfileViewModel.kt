package com.paya.presentation.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.hint.fragments.CardAccount
import com.paya.presentation.utils.*
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
	private val getBoxHistoryUseCase: UseCase<BoxHistoryRequestModel, BoxHistoryRepoModel>,
	private val existAccountUseCase: UseCase<Unit, ExitAccountRepoModel>
) : BaseViewModel() {

	val profile = MutableLiveData<Resource<BoxHistoryRepoModel>>()
	val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
	val loading = MediatorLiveData<Resource<Nothing>>()
	val errorMessage = MutableLiveData<String?>(null)

	val boxHistoryHahMap = mutableMapOf<Long, BoxHistoryRepoModel?>()
	var currentBoxId: Long? = null
	var boxHistoryId: List<Long>? = null
	val cardAccounts = mutableListOf<CardAccount>()
	var boxHistoryType = FilterProfile.day
	var boxHistoryNumber = 3
	var currentDate = PersianCalendar()
	var xListChart = mutableListOf<String>()

	init {
		loading.addSource(profile) {
			if (profile.value?.status == Status.LOADING || existAccount.value?.status == Status.LOADING) {
				loading.value = Resource.loading(null)
			} else {
				loading.value = Resource.idle(null)
			}
		}
		loading.addSource(existAccount) {
			if (profile.value?.status == Status.LOADING || existAccount.value?.status == Status.LOADING) {
				loading.value = Resource.loading(null)
			} else {
				loading.value = Resource.idle(null)
			}
		}
	}
	
	fun setErrorMessage(message: String){
		errorMessage.value = message
	}
	fun getExistAccount(){
		existAccount.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			existAccount.postValue(callResource(this@ProfileViewModel,existAccountUseCase.action(Unit)))
		}
	}

	fun getProfile(
		boxId: Long,
		type: FilterProfile,
		number: Int
	) {
		profile.value?.let {
			if(it.status == Status.LOADING)
				return
		}
		profile.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			profile.postValue(
				callResource(
					this@ProfileViewModel, getBoxHistoryUseCase.action(
						BoxHistoryRequestModel(boxId, type.name, number)
					)
				)
			)
		}
	}

	fun xListChart() {
		val list = mutableListOf<String>()
		when(boxHistoryType) {
			FilterProfile.day -> {
				for (index in 11 downTo 1) {
					list.add(
						getBeforeHour(
							currentDate.timeInMillis,
							index * -1
						).getHourPersianDate().toString()
					)
				}
				list.add(currentDate.getHourPersianDate().toString())
			}
			FilterProfile.month -> {
				for (index in 11 downTo 1) {
					list.add(
						getBeforeDay(
							currentDate.timeInMillis,
							index * -1
						).persianShortDate
					)
				}
				list.add(currentDate.persianShortDate)
			}
			FilterProfile.week -> {
				for (index in 6 downTo 1) {
					list.add(
						getBeforeDay(
							currentDate.timeInMillis,
							index * -1
						).persianWeekDayName
					)
				}
				list.add(currentDate.persianWeekDayName)
			}
			FilterProfile.years -> {
				for (index in 11 downTo 1) {
					list.add(
						getBeforeMonth(
							currentDate.timeInMillis,
							index * -1
						).persianMonthName
					)
				}
				list.add(currentDate.persianMonthName)
			}
		}


		list.forEachIndexed { index, s ->
			Log.d("dayInMonth", "$index : $s")
		}
	}
	/*fun getExistAccount(){
		existAccount.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			existAccount.postValue(getMockE())
		}
	}

	private fun getMockE() : Resource<ExitAccountRepoModel> {
		return Resource.success(ExitAccountRepoModel(true,listOf(1,2)))
	}

	fun getProfile(
		boxId: Long,
		type: String,
		number: Int
	) {
		profile.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			delay(1500)
			profile.postValue(
				getMockData()
			)
		}
	}
	
	private fun getMockData(): Resource<BoxHistoryRepoModel> {
		val cardChart = LinearChartRepoModel(
			listOf(1796666,158666,1656666,1656666,158666,1696666,1736666,1795666,1796666),
			"2020-11-20T09:13:24.700966Z","2020-11-10T09:13:24.700966Z"
		)
		val mainChart = LinearChartRepoModel(
			listOf(156666,158666,1656666,1696666,1736666,1796666),
			"2020-11-30T09:13:24.700966Z","2020-10-30T09:13:24.700966Z"
		)
		val circleChart = listOf(
			CircleChartDataRepoModel(250000F,3,"فیلان"),
			CircleChartDataRepoModel(300000F,6,"بیسار2"),
			CircleChartDataRepoModel(300000F,2,"بیسار"),
			CircleChartDataRepoModel(300000F,4,"بیسار1")
		)
		val boxRepoModel = BoxHistoryRepoModel(
			cardChart,
			mainChart,
			circleChart,
			196000,
			0.6f,
			"امید نقی پور"
		)
		return Resource.success(boxRepoModel)
	}*/

	enum class FilterProfile {
		day,
		week,
		month,
		years
	}
	
}