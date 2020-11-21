package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.shared.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
	private val getProfileUseCase: UseCase<Unit,ProfileRepoModel>
): ViewModel(){
	
	val pointsLiveData = MutableLiveData<List<Point>>()
	val profile = MutableLiveData<Resource<ProfileRepoModel>>()
	
	fun getPoints() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		pointsLiveData.value = points
	}
	
	fun getProfile(){
		viewModelScope.launch(Dispatchers.IO){
			profile.postValue(getProfileUseCase.action(Unit))
		}
	}
	
}