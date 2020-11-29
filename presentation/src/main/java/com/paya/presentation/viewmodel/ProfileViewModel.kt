package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.shared.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
	private val getBoxHistoryUseCase: UseCase<BoxHistoryRequestModel,BoxHistoryRepoModel>,
	private val existAccountUseCase: UseCase<Unit,ExitAccountRepoModel>
): ViewModel(){
	
	val pointsLiveData = MutableLiveData<List<Point>>()
	val profile = MutableLiveData<Resource<BoxHistoryRepoModel>>()
	val existAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
	
	fun getPoints() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		pointsLiveData.value = points
	}
	
	fun getExistAccount(){
		viewModelScope.launch(Dispatchers.IO) {
			existAccount.postValue(existAccountUseCase.action(Unit))
		}
	}
	
	fun getProfile(
		boxId: Long,
		type: String,
		number: Int
	){
		viewModelScope.launch(Dispatchers.IO){
			profile.postValue(getBoxHistoryUseCase.action(
				BoxHistoryRequestModel(boxId, type, number)
			))
		}
	}
	
}