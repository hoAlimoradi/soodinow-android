package com.paya.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel @ViewModelInject constructor(
	private val getAllQuestionsUseCase: UseCase<Unit,ArrayList<QuestionsRepoModel>>
): BaseViewModel(){
	
	val questions = MutableLiveData<Resource<List<QuestionsRepoModel>>>()
	
	fun getAllQuestions(){
		questions.value = Resource.loading(null)
		viewModelScope.launch(Dispatchers.IO) {
			questions.postValue(callResource(this@QuestionViewModel,getAllQuestionsUseCase.action(Unit)))
		}
	}
	
}