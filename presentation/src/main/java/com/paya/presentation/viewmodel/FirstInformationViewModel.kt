package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
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
	val firstName = ObservableField<String>()
	val lastName = ObservableField<String>()
	val phone = ObservableField<String>()
	val email = ObservableField<String>()
	val nationalCode = ObservableField<String>()
	val birthDay = ObservableField<PersianCalendar>()
	val bban = ObservableField<String>()
	val gender = ObservableField<String>()
	val province = ObservableField<String>()
	val city = ObservableField<String>()
	val address = ObservableField<String>()
	val cityList = mutableListOf<ProvinceRepoModel>()
	var citySelection = 0
	var provinceSelection = 0
	val loading = MediatorLiveData<Resource<Nothing>>()
	val statusUpdate = MutableLiveData<Resource<ProfileRepoModel>>()
	val statusCity = MutableLiveData<Resource<List<ProvinceRepoModel>>>()
	val statusProfile = MutableLiveData<Resource<ProfileRepoModel>>()


	init {
		loading.addSource(statusProfile){
			if (statusProfile.value?.status == Status.LOADING || statusCity.value?.status == Status.LOADING){
				loading.value = Resource.loading(null)
			}else{
				loading.value = Resource.idle(null)
			}
		}
		loading.addSource(statusUpdate){
			if (statusUpdate.value?.status == Status.LOADING) {
				loading.value = Resource.loading(null)
			}else{
				loading.value = Resource.idle(null)
			}
		}
		loading.addSource(statusCity){
			if (statusCity.value?.status == Status.LOADING || statusProfile.value?.status == Status.LOADING){
				loading.value = Resource.loading(null)
			}else{
				loading.value = Resource.idle(null)
			}
		}
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
			statusUpdate.postValue(Resource.loading(null))
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
		}
	}

	fun getCity() {
		viewModelScope.launch {
			statusCity.postValue(Resource.loading(null))
			val response = callResource(this@FirstInformationViewModel, cityUseCase.action(Unit))
			if (response.status == Status.SUCCESS)
				cityList.addAll(response.data!!)
			statusCity.postValue(response)
		}
	}

	fun getProfile() {
		viewModelScope.launch {
			statusProfile.postValue(Resource.loading(null))
			val response  = callResource(this@FirstInformationViewModel,useCaseProfile.action(Unit))
			if (response.status == Status.SUCCESS && response.data?.complete!!) {
				firstName.set(response.data?.firstName)
				lastName.set(response.data?.lastName)
				phone.set(response.data?.phone)
				email.set(response.data?.email)
				bban.set(response.data?.bban?.replace("IR",""))
				birthDay.set(response.data?.birthDay?.let { Utils.convertStringToPersianCalender(it) })
				nationalCode.set(response.data?.personalCode)
				gender.set(response.data?.gender)
				province.set(response.data?.state)
				city.set(response.data?.city)
				address.set(response.data?.address)
			}
			statusProfile.postValue(response)
		}

	}
}