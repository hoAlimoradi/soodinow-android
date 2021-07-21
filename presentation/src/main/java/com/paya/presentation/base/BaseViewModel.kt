package com.paya.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.GetAuthLinkRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.GetAuthLinkUseCase
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.launch
import javax.inject.Inject
const val REMAINING_TIME = 120
abstract class BaseViewModel : ViewModel() {
	@Inject
	protected lateinit var getAuthLinkUseCase: UseCase<String,GetAuthLinkRepoModel>
	val unAuthorizeLiveData = MutableLiveData<String>()
	val errorLiveData = MutableLiveData<String>()
	val unFarabiAuth = MutableLiveData<Resource<GetAuthLinkRepoModel>>()
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
		viewModelScope.launch {
			showLoading()
			val response = callResource(this@BaseViewModel,getAuthLinkUseCase.action("app://soodinow.com"))
			unFarabiAuth.postValue(response)
			hideLoading()
		}

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