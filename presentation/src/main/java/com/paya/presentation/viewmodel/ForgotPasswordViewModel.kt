package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordViewModel @ViewModelInject constructor(
	private val resetPasswordUseCase: UseCase<String, ResetPasswordRepoModel>
): BaseViewModel(){
	
	val title = MutableLiveData<String>()
	
	val phoneNumber = ObservableField<String>()
	
	val resetPasswordStatus = VolatileLiveData<Resource<ResetPasswordRepoModel>>()
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun register(){
		val phoneNumber = phoneNumber.get()
		if (phoneNumber == null) {
			resetPasswordStatus.setValue(Resource.error("لطفا شماره موبایل را وارد کنید", null))
			return
		}
		if(phoneNumber.length != 9){
			resetPasswordStatus.setValue(Resource.error("شماره موبایل وارد شده شده اشتباه است", null))
			return
		}
		viewModelScope.launch (Dispatchers.IO) {
			resetPasswordStatus.postValue(Resource.loading(null))
			val response = callResource(this@ForgotPasswordViewModel,resetPasswordUseCase.action("+989$phoneNumber"))
			resetPasswordStatus.postValue(response)
		}
	}
	
}