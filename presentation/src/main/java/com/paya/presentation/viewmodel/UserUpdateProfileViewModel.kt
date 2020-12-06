package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserUpdateProfileViewModel @ViewModelInject constructor(
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>,
	private val useCaseUpdateProfile: UseCase<ProfileBodyRepoModel,ProfileRepoModel>
) : BaseViewModel() {
	val statusGetProfile = VolatileLiveData<Resource<ProfileRepoModel>>()
	val statusUpdateProfile = VolatileLiveData<Resource<ProfileRepoModel>>()
	val loading = MediatorLiveData<Resource<Nothing>>()
	
	
	val name = ObservableField<String>()
	val bban = ObservableField<String>()
	val nationalCode = ObservableField<String>()
	val date = ObservableField<PersianCalendar>()
	
	
	init {
		loading.addSource(statusGetProfile) {
			if (statusGetProfile.value?.status == Status.LOADING) {
				loading.value = Resource.loading(null)
			} else {
				loading.value = Resource.idle(null)
			}
		}
		loading.addSource(statusUpdateProfile) {
			if (statusUpdateProfile.value?.status == Status.LOADING) {
				loading.value = Resource.loading(null)
			} else {
				loading.value = Resource.idle(null)
			}
		}
		getProfile()
	}
	

	
	fun updateProfile() {
		val name = name.get()
		val nationalCode = nationalCode.get()
		val birthDay = date.get()?.let { Utils.convertToDate(it) }
		val bban = bban.get()
		
		if (name.isNullOrBlank()) {
			statusUpdateProfile.setValue(Resource.error("name can not be blank",null))
			return
		}
		
		if (nationalCode.isNullOrBlank()) {
			statusUpdateProfile.setValue(Resource.error("nationalCode can not be blank",null))
			return
		}
		
		if (nationalCode.length != 10) {
			statusUpdateProfile.setValue(Resource.error("nationalCode is not valid",null))
			return
		}
		
		if (birthDay.isNullOrBlank()) {
			statusUpdateProfile.setValue(Resource.error("birthDay can not be blank",null))
			return
		}
		
		if (bban.isNullOrBlank()) {
			statusUpdateProfile.setValue(Resource.error("shaba can not be blank",null))
			return
		}
		viewModelScope.launch(Dispatchers.IO) {
			statusUpdateProfile.postValue(Resource.loading(null))
			val body = ProfileBodyRepoModel(
				name,
				nationalCode,
				birthDay,
				"IR${bban}"
			)
			val response = callResource(this@UserUpdateProfileViewModel,useCaseUpdateProfile.action(body))
			statusUpdateProfile.postValue(response)
		}
	}
	
	private fun getProfile() {
		viewModelScope.launch(Dispatchers.IO) {
			statusGetProfile.postValue(Resource.loading(null))
			val response =callResource(this@UserUpdateProfileViewModel, useCaseProfile.action(Unit))
			name.set(response.data?.name)
			bban.set(response.data?.bban?.replace("IR",""))
			date.set(response.data?.birthDay?.let { Utils.convertStringToPersianCalender(it) })
			nationalCode.set(response.data?.personalCode)
			statusGetProfile.postValue(response)
		}
		
	}
}