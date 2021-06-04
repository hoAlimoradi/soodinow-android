package com.paya.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.models.repo.SetResetPasswordRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.isSecretPassword
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class SetNewPasswordViewModel @Inject constructor(
	private val getUserInfoUseCase: UseCase<Unit,UserInfoRepoModel>,
	private val setPasswordUseCase: UseCase<String,SetResetPasswordRepoModel>
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
	
	val setPasswordResource = MutableLiveData<Resource<SetResetPasswordRepoModel>>()
	
	private fun getAccessToken() {
		viewModelScope.launch {
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
			showError("لطفا رمز عبور را وارد کنید")
			return
		}

		if (!password.isSecretPassword()) {
			showError("پسورد باید از ۸ کارکتر بیشتر باشد و از حروف بزرگ و کوچک و نماد استفاده شود")
			return
		}
		if (repeatPassword.isNullOrBlank()) {
			isRepeatPassword = true
			showError("لطفا تکرار رمز عبور را وارد کنید")
			return
		}
		
		if (password != repeatPassword){
			isRepeatPassword = true
			showError("رمز عبور با تکرار آن یکسان نیست")
			return
		}

		viewModelScope.launch {
			setPasswordResource.postValue(Resource.loading(null))
			val resource = callResource(this@SetNewPasswordViewModel,setPasswordUseCase.action(password.md5()!!))
			setPasswordResource.postValue(resource)
		}
		
	}
	
}