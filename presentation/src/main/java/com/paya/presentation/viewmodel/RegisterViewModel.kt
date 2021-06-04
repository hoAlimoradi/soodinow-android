package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class RegisterViewModel @Inject constructor(
	private val registerUseCase: UseCase<String, RegisterRepoModel>
): BaseViewModel(){
	
	val title = MutableLiveData<String>()
	
	val phoneNumber = ObservableField<String>()
	
	val registerStatus = MutableLiveData<Resource<RegisterRepoModel>>()
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun register(){
		val phoneNumber = phoneNumber.get()
		if (phoneNumber == null) {
			registerStatus.setValue(Resource.error("لطفا شماره موبایل را وارد کنید", null))
			return
		}
		if(phoneNumber.length != 9){
			registerStatus.setValue(Resource.error("شماره موبایل وارد شده اشتباه است", null))
			return
		}
		viewModelScope.launch {
			registerStatus.postValue(Resource.loading(null))
			val response = callResource(this@RegisterViewModel,registerUseCase.action("+989$phoneNumber"))
			registerStatus.postValue(response)
		}
	}
	
}