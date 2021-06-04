package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DeveloperNameViewModel @Inject constructor(
	private val getNameCase: UseCase<Unit,DeveloperNameRepoModel>
) : BaseViewModel() {
	
	val developerName = MutableLiveData<Resource<DeveloperNameRepoModel>>()
	
	fun getDeveloperName() {
		viewModelScope.launch {
			val res = callResource(this@DeveloperNameViewModel,getNameCase.action(Unit))
			withContext(Dispatchers.Main) {
				developerName.value = res
			}
		}
	}
	
}