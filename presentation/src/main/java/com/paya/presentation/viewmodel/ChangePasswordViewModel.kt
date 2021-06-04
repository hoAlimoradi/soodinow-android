package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ChangePasswordRepoBodyModel
import com.paya.domain.models.repo.ChangePasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.isSecretPassword
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
	private val changePasswordUseCase: UseCase<ChangePasswordRepoBodyModel,ChangePasswordRepoModel>
) : BaseViewModel() {
	
	var oldPassword = ObservableField<String>()
	var newPassword = ObservableField<String>()
	var repeatPassword = ObservableField<String>()
	
	var status = MutableLiveData<Resource<ChangePasswordRepoModel>>()
	
	fun changePassword() {
		val oldPassword = oldPassword.get()
		val newPassword = newPassword.get()
		val repeatPassword = repeatPassword.get()
		if (oldPassword.isNullOrEmpty()) {
			showError("لطفا پسورد خود را وارد کنید")
			return
		}
		
		if (newPassword.isNullOrEmpty()) {
			showError("لطفا پسورد جدید خود را وارد کنید")
			return
		}

		if (!newPassword.isSecretPassword()) {
			showError("پسورد باید از ۸ کارکتر بیشتر باشد و از حروف بزرگ و کوچک استفاده شود")
			return
		}
		
		if(repeatPassword.isNullOrEmpty()) {
			showError("لطفا تکرار پسورد جدید خود را وارد کنید")
			return
		}
		
		if (newPassword != repeatPassword) {
			showError("پسورد حدید با تکرار آن بکسان نیست")
			return
		}
		
		viewModelScope.launch {
			status.postValue(Resource.loading(null))
			val body = ChangePasswordRepoBodyModel(
				oldPassword.md5()!!,
				newPassword.md5()!!
			)
			status.postValue(callResource(this@ChangePasswordViewModel,changePasswordUseCase.action(body)))
		}
	}
}