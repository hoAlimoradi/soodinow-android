package com.paya.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.RiskAssessmentPagesRepoModel
import com.paya.domain.models.repo.RiskAssessmentResponseRepoModel
import com.paya.domain.models.repo.RiskAssessmentSubmitResponseRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.loge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RiskAssessmentViewModel @Inject constructor(
    private val getRiskAssessmentQuestionsUseCase: UseCase<Unit, RiskAssessmentResponseRepoModel>,
    private val submitRiskAssessmentAnswersQuestionsUseCase: UseCase<RiskAssessmentRequestAnswerBodyModel, RiskAssessmentSubmitResponseRepoModel>

) : BaseViewModel() {


    val riskAssessmentSubmitLiveData = MutableLiveData<Resource<RiskAssessmentSubmitResponseRepoModel>>()

    var pageCount = 0

    var count = 0

    var questionsCount = 0

    val questionsMap = mutableMapOf<Int, Boolean>()

    //val questionsCountLiveData :MutableLiveData<Int> = MutableLiveData(0)

    var questionsCountPercentLiveData :MutableLiveData<Int> = MutableLiveData(0)

    val riskAssessmentPagesLiveData = MutableLiveData<Resource<RiskAssessmentResponseRepoModel>>()

    var assessYourRiskQuestionsViewPagerCurrentPageLiveData = MutableLiveData<Int>()

    var riskAssessmentResponseRepoModel: RiskAssessmentResponseRepoModel? = null

    var riskAssessmentRequestAnswerMap = mutableMapOf<Int, RiskAssessmentRequestAnswer>()

    var riskAssessmentRequestAnswerList: ArrayList<RiskAssessmentRequestAnswer> = arrayListOf()

    var assessYourRiskNextStateListLiveData  = MutableLiveData<List<AssessYourRiskNextState>>()

    fun getRiskAssessmentQuestions() {
        viewModelScope.launch {
            val response = callResource(this@RiskAssessmentViewModel,getRiskAssessmentQuestionsUseCase.action(Unit))
            when (response.status) {
                Status.SUCCESS -> response.data?.let {
                    riskAssessmentResponseRepoModel = it
                    pageCount = it.pageCount
                    count = it.count
                    loge( " riskAssessmentPages.questionCount it " + it.count   )

                    val  assessYourRiskNextStateList :ArrayList<AssessYourRiskNextState> = arrayListOf()
                    for (counter in 0..pageCount) {
                        assessYourRiskNextStateList.add(AssessYourRiskNextState(pageNumber = counter, nextIsActive = false))
                    }
                    assessYourRiskNextStateListLiveData.value = assessYourRiskNextStateList
                }
                else -> {
                    loge( " RiskAssessmentResponseRepoModel is null " )
                }
            }
            riskAssessmentPagesLiveData.postValue(response)
            //hideLoading()
        }
    }

    fun setAssessYourRiskQuestionsViewPagerCurrentPage(currentPosition: Int) {
        viewModelScope.launch {
            assessYourRiskQuestionsViewPagerCurrentPageLiveData.value = currentPosition
        }
    }

    fun updateQuestionsMap(riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer) {
        if (riskAssessmentRequestAnswer.answers.isNotEmpty()) {
            questionsMap.put(riskAssessmentRequestAnswer.question.id, true)
        } else {
            questionsMap.put(riskAssessmentRequestAnswer.question.id, false)
        }

        var questionsCountHasAnswer = 0
        questionsMap.forEach{
            if (it.value) {
                questionsCountHasAnswer++
            }
        }
        questionsCountPercentLiveData.value = ( (questionsCountHasAnswer* 100)  /count).toInt()

    }

    fun getRiskAssessmentQuestionsByPageNumber(pageNumber: Int): RiskAssessmentPagesRepoModel  {
        val pages = riskAssessmentResponseRepoModel!!.pages[pageNumber - 1]
        return pages
    }

    fun saveRiskAssessmentRequestAnswer(pageNumber: Int, riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer){
        riskAssessmentRequestAnswerMap[riskAssessmentRequestAnswer.question.id] = riskAssessmentRequestAnswer
        updateQuestionsMap(riskAssessmentRequestAnswer)
        updateRiskAssessmentResponseRepoModel(pageNumber, riskAssessmentRequestAnswer.question.id)
    }

    fun submitRiskAssessmentRequestAnswer(){
        viewModelScope.launch {
            showLoading()
            riskAssessmentRequestAnswerList.clear()

            riskAssessmentRequestAnswerMap.forEach{
                riskAssessmentRequestAnswerList.add(it.value)
            }
            val riskAssessmentRequestAnswerRepoBodyModel = RiskAssessmentRequestAnswerBodyModel(riskAssessmentRequestAnswerList)
            riskAssessmentSubmitLiveData.value = submitRiskAssessmentAnswersQuestionsUseCase.action(riskAssessmentRequestAnswerRepoBodyModel)

            hideLoading()
        }

    }

    fun updateRiskAssessmentResponseRepoModel(pageNumber: Int, questionId: Int)  {

        riskAssessmentResponseRepoModel?.let {

            it.pages[pageNumber].questions.filter {
                it.id == questionId
            }.single().run {
                this.IsAnsweredByTheUser = true
            }
        }
        /*viewModelScope.launch {
            assessYourRiskNextStateListLiveData.value?.let {
                it[pageNumber].nextIsActive = checkWhetherAssessYourRiskNextIsActiveOrNot(pageNumber)
            }
        }*/


    }

    fun checkWhetherAssessYourRiskNextIsActiveOrNot(pageNumber: Int): Boolean  {
        riskAssessmentResponseRepoModel?.let {
            it.pages[pageNumber].questions.forEach {
                if (!it.IsAnsweredByTheUser) {
                    return false
                }
            }
        }
        return true
    }


}

data class AssessYourRiskNextState(
    val pageNumber: Int,
    var nextIsActive: Boolean
)