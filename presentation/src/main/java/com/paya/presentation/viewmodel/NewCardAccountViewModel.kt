package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCardAccountViewModel @ViewModelInject constructor(
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>
) : BaseViewModel() {

	val statusProfile = VolatileLiveData<Resource<ProfileRepoModel>>()
	
	fun getProfile() {
		viewModelScope.launch(Dispatchers.IO) {
			statusProfile.postValue(Resource.loading(null))
			val response  = callResource(this@NewCardAccountViewModel,useCaseProfile.action(Unit))
			statusProfile.postValue(response)
		}
		
	}
}