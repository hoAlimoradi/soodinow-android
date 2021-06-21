package com.paya.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
	val unAuthorizeLiveData = MutableLiveData<String>()
	val errorLiveData = MutableLiveData<String>()
	val unFarabiAuth = MutableLiveData<Unit>()
	val unExistProfileUser = MutableLiveData<Unit>()
	val unLoading = MutableLiveData<Boolean>()
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

	fun farabiAuth() {
		unFarabiAuth.postValue(Unit)
	}

	fun existProfileUser() {
		unExistProfileUser.postValue(Unit)
	}

	fun showError(error: String, isShowError: Boolean = true) {
		if (!isShowError)
			return
		errorLiveData.postValue(error)
	}

	fun showLoading() {
		unLoading?.value?.let {
			if (it)
				return
		}
		unLoading.value = true
	}

	fun hideLoading() {
		unLoading.value = false
	}
}