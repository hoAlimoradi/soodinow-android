package com.paya.presentation.viewmodel

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
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
	private val changePasswordUseCase: UseCase<ChangePasswordRepoBodyModel,ChangePasswordRepoModel>
) : BaseViewModel() {


	var status = MutableLiveData<Resource<ChangePasswordRepoModel>>()

	fun changePassword(
		oldPassword: String,
		newPassword: String
	) {
		viewModelScope.launch {
			showLoading()
			val body = ChangePasswordRepoBodyModel(
				oldPassword.md5()!!,
				newPassword.md5()!!
			)
			status.postValue(callResource(this@ChangePasswordViewModel,changePasswordUseCase.action(body)))
			hideLoading()
		}
	}
}