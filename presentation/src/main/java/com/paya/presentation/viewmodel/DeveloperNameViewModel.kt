package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DeveloperNameViewModel @ViewModelInject constructor(
	private val getNameCase: UseCase<Unit,DeveloperNameRepoModel>
) : BaseViewModel() {
	
	val developerName = MutableLiveData<Resource<DeveloperNameRepoModel>>()
	
	fun getDeveloperName() {
		viewModelScope.launch(Dispatchers.IO) {
			val res = getNameCase.action(Unit)
			withContext(Dispatchers.Main) {
				developerName.value = res
			}
		}
	}
	
}