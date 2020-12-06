package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.GetProfileUseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel @ViewModelInject constructor(
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>
) : BaseViewModel() {

	init {
		getProfile()
	}
	val status = VolatileLiveData<Resource<ProfileRepoModel>>()
	val mobile = ObservableField<String>()
	fun getProfile() {
		viewModelScope.launch(Dispatchers.IO) {
			status.postValue(Resource.loading(null))
			val response  = callResource(this@SettingViewModel,useCaseProfile.action(Unit))
			status.postValue(response)
		}
		
	}
}