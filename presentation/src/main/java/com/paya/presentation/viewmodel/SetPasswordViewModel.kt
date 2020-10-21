package com.paya.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.AccessTokenRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.VolatileLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetPasswordViewModel @ViewModelInject constructor(
	private val getAccessTokenUseCase: UseCase<Unit,AccessTokenRepoModel>,
	private val setPasswordUseCase: UseCase<String,SetPasswordRepoModel>
) : ViewModel() {
	
	private var accessToken: String? = null
	
	init {
		getAccessToken()
	}
	
	val setPasswordResource = VolatileLiveData<Resource<SetPasswordRepoModel>>()
	
	private fun getAccessToken() {
		viewModelScope.launch(Dispatchers.IO) {
			val accessTokenResource = getAccessTokenUseCase.action(Unit)
			accessToken = accessTokenResource.data?.accessToken
			Log.d("SetPasswordViewModel", accessToken ?: "null")
		}
	}
	
	val password = ObservableField<String>()
	val repeatPassword = ObservableField<String>()
	
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
			val resource = setPasswordUseCase.action(password)
			setPasswordResource.postValue(resource)
		}
		
	}
	
}