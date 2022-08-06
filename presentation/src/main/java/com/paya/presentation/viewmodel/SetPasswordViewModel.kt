package com.paya.presentation.viewmodel

import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent

import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.md5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class SetPasswordViewModel @Inject constructor(
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

	
	val setPasswordResource = SingleLiveEvent<Resource<SetPasswordRepoModel>>()
	
	private fun getAccessToken() {
		viewModelScope.launch{
			val accessTokenResource = callResource(this@SetPasswordViewModel,getUserInfoUseCase.action(Unit))
			accessToken = accessTokenResource.data?.accessToken
			Log.d("SetPasswordViewModel", accessToken ?: "null")
		}
	}
	
	fun setTitle(title: String) {
		this.title.value = title
	}
	
	fun submit(password:String){
		viewModelScope.launch {
			showLoading()
			val resource = callResource(this@SetPasswordViewModel,setPasswordUseCase.action(password.md5()!!))
			setPasswordResource.postValue(resource)
			hideLoading()
		}
		
	}
	
}