package com.paya.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.VolatileLiveData
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetNewPasswordViewModel @ViewModelInject constructor(
	private val getUserInfoUseCase: UseCase<Unit,UserInfoRepoModel>,
	private val setPasswordUseCase: UseCase<String,SetPasswordRepoModel>
) : BaseViewModel() {

	var isNewPassword = false
	var isRepeatPassword = false
	val title = MutableLiveData<String>()
	private var accessToken: String? = null

	init {
		getAccessToken()
	}

	val password = ObservableField<String>()
	val repeatPassword = ObservableField<String>()
	
	val setPasswordResource = VolatileLiveData<Resource<SetPasswordRepoModel>>()
	
	private fun getAccessToken() {
		viewModelScope.launch(Dispatchers.IO) {
			val accessTokenResource = callResource(this@SetNewPasswordViewModel,getUserInfoUseCase.action(Unit))
			accessToken = accessTokenResource.data?.accessToken
			Log.d("SetPasswordViewModel", accessToken ?: "null")
		}
	}
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun submit() {
		isNewPassword = false
		isRepeatPassword = false
		val password = password.get()
		val repeatPassword = repeatPassword.get()

		if (password.isNullOrBlank()) {
			isNewPassword = true
			setPasswordResource.setValue(
				Resource.error("لطفا رمز عبور را وارد کنید", null)
			)
			return
		}
		if (repeatPassword.isNullOrBlank()) {
			isRepeatPassword = true
			setPasswordResource.setValue(
				Resource.error("لطفا تکرار رمز عبور را وارد کنید", null)
			)
			return
		}
		
		if (password != repeatPassword){
			isRepeatPassword = true
			setPasswordResource.setValue(
				Resource.error("رمز عبور با تکرار آن یکسان نیست", null)
			)
			return
		}

		viewModelScope.launch(Dispatchers.IO) {
			setPasswordResource.postValue(Resource.loading(null))
			val resource = callResource(this@SetNewPasswordViewModel,setPasswordUseCase.action(password))
			setPasswordResource.postValue(resource)
		}
		
	}
	
}