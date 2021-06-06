package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
	private val resetPasswordUseCase: UseCase<String, ResetPasswordRepoModel>
) : BaseViewModel() {

	val title = MutableLiveData<String>()

	val resetPasswordStatus = MutableLiveData<Resource<ResetPasswordRepoModel>>()

	fun setTitle(title: String) {
		this.title.value = title
	}

	fun resetPassword(phoneNumber: String) {
		viewModelScope.launch {
			showLoading()
			val response = callResource(
				this@ForgotPasswordViewModel,
				resetPasswordUseCase.action("+989$phoneNumber")
			)
			resetPasswordStatus.postValue(response)
			hideLoading()
		}
	}
	
}