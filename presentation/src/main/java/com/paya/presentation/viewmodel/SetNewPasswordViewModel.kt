package com.paya.presentation.viewmodel

import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.SetResetPasswordRepoModel
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class SetNewPasswordViewModel @Inject constructor(
	private val getUserInfoUseCase: UseCase<Unit,UserInfoRepoModel>,
	private val setPasswordUseCase: UseCase<String,SetResetPasswordRepoModel>
) : BaseViewModel() {


	val title = MutableLiveData<String>()
	private var accessToken: String? = null
	val setPasswordResource = SingleLiveEvent<Resource<SetResetPasswordRepoModel>>()

	init {
		getAccessToken()
	}


	
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
	
	fun submit(password:String) {
		viewModelScope.launch {
			showLoading()
			val resource = callResource(this@SetNewPasswordViewModel,setPasswordUseCase.action(password.md5()!!))
			setPasswordResource.postValue(resource)
			hideLoading()
		}
		
	}
	
}