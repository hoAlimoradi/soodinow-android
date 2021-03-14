package com.paya.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
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
	
	fun submit(){
		val password = password.get()
		val repeatPassword = repeatPassword.get()
		
		if (password.isNullOrBlank() || repeatPassword.isNullOrBlank()){
			setPasswordResource.setValue(
				Resource.error("Passwords can not be empty", null)
			)
			return
		}
		
		if (password != repeatPassword){
			setPasswordResource.setValue(
				Resource.error("Passwords are not equal", null)
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