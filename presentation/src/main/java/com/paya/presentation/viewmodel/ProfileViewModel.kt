package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
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
	val loading = MediatorLiveData<Resource<Nothing>>()
	
	init {
		loading.addSource(profile){
			if (profile.value?.status == Status.LOADING || existAccount.value?.status == Status.LOADING){
				loading.value = Resource.loading(null)
			}else{
				loading.value = Resource.idle(null)
			}
		}
		loading.addSource(existAccount){
			if (profile.value?.status == Status.LOADING || existAccount.value?.status == Status.LOADING){
				loading.value = Resource.loading(null)
			}else{
				loading.value = Resource.idle(null)
			}
		}
	}
	
	fun getPoints() {
		val points = mutableListOf<Point>()
		for (i in 0 until 10) {
			val value = (Math.random() * 100).toFloat()
			points.add(Point(i.toFloat(),value))
		}
		pointsLiveData.value = points
	}
	
	fun getExistAccount(){
		existAccount.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			existAccount.postValue(existAccountUseCase.action(Unit))
		}
	}
	
	fun getProfile(
		boxId: Long,
		type: String,
		number: Int
	){
		profile.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO){
			profile.postValue(getBoxHistoryUseCase.action(
				BoxHistoryRequestModel(boxId, type, number)
			))
		}
	}
	
}