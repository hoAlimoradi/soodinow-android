package com.example.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.domain.tools.UseCase
import com.example.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class DeveloperNameViewModel @ViewModelInject constructor(
	private val getNameCase : UseCase<Unit,DeveloperNameRepoModel>
) : BaseViewModel(){
		
	val developerName = MutableLiveData<DeveloperNameRepoModel>()
	
	fun getDeveloperName(){
		viewModelScope.launch(Dispatchers.IO) {
			val res = getNameCase.action(Unit)
			withContext(Dispatchers.Main){
				developerName.value = res
			}
		}
	}
	
}