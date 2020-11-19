package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.GetProfileUseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculateProfitCapitalViewModel @ViewModelInject constructor(
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>
) : ViewModel() {

	val statusProfile = VolatileLiveData<Resource<ProfileRepoModel>>()
	
	fun getProfile() {
		viewModelScope.launch(Dispatchers.IO) {
			statusProfile.postValue(Resource.loading(null))
			val response  = useCaseProfile.action(Unit)
			statusProfile.postValue(response)
		}
		
	}
}