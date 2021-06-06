package com.paya.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ActivateResetPasswordRepoModel
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateForgotPasswordViewModel @Inject constructor(
	private val activateUseCase: UseCase<ActivateResetPasswordRepoModel,Any>,
	private val resetPasswordUseCase: UseCase<String,ResetPasswordRepoModel>
) : BaseViewModel() {
	
	val title = MutableLiveData<String>()
	val remainingTime = MutableLiveData(59)
	
	init {
		setRemainingTime()
	}

	lateinit var phoneNumber: String

	val status = MutableLiveData<Resource<Any>>()

	fun setTitle(title: String) {
		this.title.value = title
	}

	fun activate(activationCode: String) {
		if (activationCode.isEmpty() || activationCode.length != 5) {
			status.setValue(Resource.error("کد وارد شده اشتباه است", null))
			return
		}
		viewModelScope.launch {
			showLoading()
			val activateModel = ActivateResetPasswordRepoModel(
				phoneNumber,
				activationCode
			)
			val response = callResource(
				this@ActivateForgotPasswordViewModel,
				activateUseCase.action(activateModel)
			)
			status.postValue(response)
			hideLoading()
		}
	}
	
	fun register() {
		if (remainingTime.value != 0)
			return
		viewModelScope.launch {
			showLoading()
			val response = callResource(this@ActivateForgotPasswordViewModel,resetPasswordUseCase.action(phoneNumber))
			
			if (response.status == Status.SUCCESS) {
				status.postValue(Resource.idle(null))
				remainingTime.postValue(59)
				setRemainingTime()
			} else
				status.postValue(response)
			hideLoading()
		}
	}
	
	
	private fun setRemainingTime() {
		viewModelScope.launch {
			val formattedRemainingTime =
				if (remainingTime.value!! >= 10) remainingTime.value.toString()
				else "0${remainingTime.value}"
			
			if (remainingTime.value == 0)
				return@launch
			
			delay(1000)
			remainingTime.value = remainingTime.value!! - 1
			
			setRemainingTime()
		}
	}
	
}