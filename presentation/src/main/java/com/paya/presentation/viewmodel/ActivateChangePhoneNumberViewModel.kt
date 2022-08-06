package com.paya.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.base.REMAINING_TIME
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.startWithCountryCodeMobile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateChangePhoneNumberViewModel @Inject constructor(
	private val verifyResetPhoneUseCase: UseCase<ResetPhoneVerifyBodyRepoModel, ResetPhoneVerifyRepoModel>,
	private val activateResetPhone: UseCase<ActivateResetPhoneBodyRepoModel, ActivateResetPhoneRepoModel>
) : BaseViewModel() {
	
	val title = MutableLiveData<String>()
	val remainingTime = MutableLiveData(REMAINING_TIME)
	
	init {
		setRemainingTime()
	}

	lateinit var phoneNumber: String
	lateinit var oldCode: String

	val status = SingleLiveEvent<Resource<Any>>()
	val statusVerify = SingleLiveEvent<Resource<ResetPhoneVerifyRepoModel>>()

	fun setTitle(title: String) {
		this.title.value = title
	}

	fun activateResetPhone(activationCode: String) {
		if (activationCode.isEmpty() || activationCode.length != 5) {
			showError("کد وارد شده اشتباه است")
			return
		}
		viewModelScope.launch {
			showLoading()
			val activateModel = ActivateResetPhoneBodyRepoModel(
				phoneNumber.startWithCountryCodeMobile(),
				activationCode
			)
			val response = callResource(
				this@ActivateChangePhoneNumberViewModel,
				activateResetPhone.action(activateModel)
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
			val body = ResetPhoneVerifyBodyRepoModel(
				phoneNumber.startWithCountryCodeMobile(),
				oldCode
			)
			val response = callResource(
				this@ActivateChangePhoneNumberViewModel,
				verifyResetPhoneUseCase.action(body)
			)
			if (response.status == Status.SUCCESS) {
				status.postValue(Resource.idle(null))
				remainingTime.value = REMAINING_TIME
				setRemainingTime()
			}
			statusVerify.postValue(response)
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