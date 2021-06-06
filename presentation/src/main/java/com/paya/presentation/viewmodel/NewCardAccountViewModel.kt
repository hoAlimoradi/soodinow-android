package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class NewCardAccountViewModel @Inject constructor(
	private val useCaseProfile: UseCase<Unit,ProfileRepoModel>
) : BaseViewModel() {

	val statusProfile = MutableLiveData<Resource<ProfileRepoModel>>()
	
	fun getProfile() {
		viewModelScope.launch {
			showLoading()
			val response  = callResource(this@NewCardAccountViewModel,useCaseProfile.action(Unit))
			statusProfile.postValue(response)
			hideLoading()
		}
		
	}
}