package com.paya.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
	val unAuthorizeLiveData = MutableLiveData<String>()
	override fun onCleared() {
		super.onCleared()
	}
	
	fun unAuthorized(
		message: String,
		block: () -> Unit
	) {
		unAuthorizeLiveData.postValue(message)
		block()
	}
}