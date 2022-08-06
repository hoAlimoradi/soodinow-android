package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.startWithCountryCodeMobile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class RegisterViewModel @Inject constructor(
	private val registerUseCase: UseCase<String, RegisterRepoModel>
): BaseViewModel(){
	
	val title = MutableLiveData<String>()
	val registerStatus = SingleLiveEvent<Resource<RegisterRepoModel>>()
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun register(phoneNumber:String){
		viewModelScope.launch {
			showLoading()
			val response = callResource(this@RegisterViewModel,registerUseCase.action(phoneNumber.startWithCountryCodeMobile()))
			registerStatus.postValue(response)
			hideLoading()
		}
	}
	
}