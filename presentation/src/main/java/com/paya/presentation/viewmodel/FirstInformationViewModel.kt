package com.paya.presentation.viewmodel


import javax.inject.Inject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class FirstInformationViewModel @Inject constructor(
	private val useCaseUpdateProfile: UseCase<ProfileBodyRepoModel, ProfileRepoModel>,
	private val cityUseCase: UseCase<Unit, List<ProvinceRepoModel>>,
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>
) : BaseViewModel() {

	val cityList = mutableListOf<ProvinceRepoModel>()
	var citySelection = 0
	var provinceSelection = 0
	var birthDay: PersianCalendar = PersianCalendar()
	val statusUpdate = MutableLiveData<Resource<ProfileRepoModel>>()
	val statusCity = MutableLiveData<Resource<List<ProvinceRepoModel>>>()
	val statusProfile = MutableLiveData<Resource<ProfileRepoModel>>()


	init {

		getCity()
		getProfile()
	}

	fun updateProfile(
		firstName: String,
		lastName: String,
		phone: String,
		email: String,
		nationalCode: String,
		birthDay: String,
		bban: String,
		gender: String,
		city: String,
		province: String,
		address: String
	) {

		viewModelScope.launch {
			showLoading()
			val body = ProfileBodyRepoModel(
				firstName,
				lastName,
				phone,
				email,
				nationalCode,
				birthDay,
				"IR$bban",
				gender,
				province,
				city,
				address
			)
			val response =
				callResource(this@FirstInformationViewModel, useCaseUpdateProfile.action(body))
			statusUpdate.postValue(response)
			hideLoading()
		}
	}

	fun getCity() {
		viewModelScope.launch {
			val response = callResource(this@FirstInformationViewModel, cityUseCase.action(Unit))
			if (response.status == Status.SUCCESS)
				cityList.addAll(response.data!!)
			statusCity.postValue(response)
		}
	}

	fun getProfile() {
		viewModelScope.launch {
			showLoading()
			val response  = callResource(this@FirstInformationViewModel,useCaseProfile.action(Unit))
			if (response.status == Status.SUCCESS && response.data?.complete!!) {

				birthDay = response.data?.birthDay?.let { Utils.convertStringToPersianCalender(it) } ?: PersianCalendar()

			}
			statusProfile.postValue(response)
			hideLoading()
		}

	}
}