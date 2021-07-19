package com.paya.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ActivateResetPasswordRepoModel
import com.paya.domain.models.repo.LoginRepoModel
import com.paya.domain.models.repo.PerLoginRepoModel
import com.paya.domain.models.repo.ResetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.startWithCountryCodeMobile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateLoginViewModel @Inject constructor(
	private val activateUseCase: UseCase<LoginRepoModel,Any>,
	private val resetPasswordUseCase: UseCase<String,PerLoginRepoModel>
) : BaseViewModel() {
	
	val title = MutableLiveData<String>()
	val remainingTime = MutableLiveData(59)
	
	init {
		setRemainingTime()
	}

	lateinit var username: String

	val status = SingleLiveEvent<Resource<Any>>()

	fun setTitle(title: String) {
		this.title.value = title
	}

	fun activate(activationCode: String) {
		if (activationCode.isEmpty() || activationCode.length != 5) {
			showError("کد وارد شده اشتباه است")
			return
		}
		viewModelScope.launch {
			showLoading()
			val activateModel = LoginRepoModel(
				username,
				activationCode
			)
			val response = callResource(
				this@ActivateLoginViewModel,
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
			val response = callResource(this@ActivateLoginViewModel,resetPasswordUseCase.action(username))
			
			if (response.status == Status.SUCCESS) {
				status.postValue(Resource.idle(null))
				remainingTime.value = 59
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