package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileBodyRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.VolatileLiveData
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstInformationViewModel @ViewModelInject constructor(
	private val useCaseUpdateProfile: UseCase<ProfileBodyRepoModel,ProfileRepoModel>
) : ViewModel() {
	val name = ObservableField<String>()
	val nationalCode = ObservableField<String>()
	val birthDay = ObservableField<PersianCalendar>()
	val bban = ObservableField<String>()
	
	val status = VolatileLiveData<Resource<ProfileRepoModel>>()
	
	fun updateProfile() {
		val name = name.get()
		val nationalCode = nationalCode.get()
		val birthDay = birthDay.get()?.let { Utils.convertToDate(it) }
		val bban = bban.get()
		
		if (name.isNullOrBlank()) {
			status.setValue(Resource.error("name can not be blank",null))
			return
		}
		
		if (nationalCode.isNullOrBlank()) {
			status.setValue(Resource.error("nationalCode can not be blank",null))
			return
		}
		
		if (nationalCode.length != 10) {
			status.setValue(Resource.error("nationalCode is not valid",null))
			return
		}
		
		if (birthDay.isNullOrBlank()) {
			status.setValue(Resource.error("birthDay can not be blank",null))
			return
		}
		
		if (bban.isNullOrBlank()) {
			status.setValue(Resource.error("shaba can not be blank",null))
			return
		}
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			val body = ProfileBodyRepoModel(
				name,
				nationalCode,
				birthDay,
				"IR$bban"
			)
			val response = useCaseUpdateProfile.action(body)
			status.postValue(response)
		}
	}
}