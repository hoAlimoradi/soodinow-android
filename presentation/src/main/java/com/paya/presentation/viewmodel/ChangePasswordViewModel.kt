package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ChangePasswordRepoBodyModel
import com.paya.domain.models.repo.ChangePasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.launch

class ChangePasswordViewModel @ViewModelInject constructor(
	private val changePasswordUseCase: UseCase<ChangePasswordRepoBodyModel,ChangePasswordRepoModel>
) : BaseViewModel() {
	
	var oldPassword = ObservableField<String>()
	var newPassword = ObservableField<String>()
	var repeatPassword = ObservableField<String>()
	
	var status = VolatileLiveData<Resource<ChangePasswordRepoModel>>()
	
	fun changePassword() {
		val oldPassword = oldPassword.get()
		val newPassword = newPassword.get()
		val repeatPassword = repeatPassword.get()
		if (oldPassword.isNullOrEmpty()) {
			status.setValue(Resource.error("لطفا پسورد خود را وارد کنید",null))
			return
		}
		
		if (newPassword.isNullOrEmpty()) {
			status.setValue(Resource.error("لطفا پسورد جدید خود را وارد کنید",null))
			return
		}
		
		if(repeatPassword.isNullOrEmpty()) {
			status.setValue(Resource.error("لطفا تکرار پسورد جدید خود را وارد کنید",null))
			return
		}
		
		if (newPassword != repeatPassword) {
			status.setValue(Resource.error("پسورد حدید با تکرار آن بکسان نیست",null))
			return
		}
		
		viewModelScope.launch {
			status.postValue(Resource.loading(null))
			val body = ChangePasswordRepoBodyModel(
				oldPassword,
				newPassword
			)
			status.postValue(callResource(this@ChangePasswordViewModel,changePasswordUseCase.action(body)))
		}
	}
}