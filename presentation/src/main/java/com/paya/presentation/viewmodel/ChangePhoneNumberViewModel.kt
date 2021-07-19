package com.paya.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
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
class ChangePhoneNumberViewModel @Inject constructor(
	private val verifyResetPhoneUseCase: UseCase<ResetPhoneVerifyBodyRepoModel,ResetPhoneVerifyRepoModel>,
	private val resetPhoneUseCase: UseCase<Unit,ResetPhoneRepoModel>
) : BaseViewModel() {
	
	val title = MutableLiveData<String>()
	val remainingTime = MutableLiveData(59)
	
	init {
		remainingTime.value = 0
		resetPhone()
	}


	val status = SingleLiveEvent<Resource<Any>>()

	fun setTitle(title: String) {
		this.title.value = title
	}

	fun verify(phone:String,activationCode: String) {
		if (activationCode.isEmpty() || activationCode.length != 5) {
			showError("کد وارد شده اشتباه است")
			return
		}
		viewModelScope.launch {
			showLoading()
			val body = ResetPhoneVerifyBodyRepoModel(
				phone.startWithCountryCodeMobile(),
				activationCode
			)
			val response = callResource(
				this@ChangePhoneNumberViewModel,
				verifyResetPhoneUseCase.action(body)
			)
			status.postValue(response)
			hideLoading()
		}
	}
	
	fun resetPhone() {
		if (remainingTime.value != 0)
			return
		viewModelScope.launch {
			showLoading()
			val response = callResource(this@ChangePhoneNumberViewModel,resetPhoneUseCase.action(Unit))
			
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