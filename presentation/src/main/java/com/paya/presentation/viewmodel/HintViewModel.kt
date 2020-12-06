package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HintViewModel @ViewModelInject constructor(
	private val getUserInfoUseCase: UseCase<Unit, UserInfoRepoModel>
): BaseViewModel(){
	
	enum class UserState{IS_HINT_SHOWED, IS_PASSWORD_SET, NONE}
	val userState = MutableLiveData<UserState>()
	
	init {
		checkUserInfo()
	}
	
	private fun checkUserInfo() = viewModelScope.launch(Dispatchers.IO){
		val userInfoResource = callResource(this@HintViewModel,getUserInfoUseCase.action(Unit))
		if (userInfoResource.status != Status.SUCCESS)
			return@launch
		
		val userInfo = userInfoResource.data ?: return@launch
		userState.postValue(getUserInfoStatus(userInfo))
	}
	
	private fun getUserInfoStatus(userInfo: UserInfoRepoModel): UserState {
		return when {
			userInfo.isPasswordSet -> {
				UserState.IS_PASSWORD_SET
			}
			userInfo.isHintShowed -> {
				UserState.IS_HINT_SHOWED
			}
			else -> {
				UserState.NONE
			}
		}
	}
	
}