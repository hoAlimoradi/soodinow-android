package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.remote.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.riskAssessment.RiskAssessmentQuestionFragment
import com.paya.presentation.ui.riskAssessment.adapter.RiskAssessmentQuestionsFragmentViewPagerAdapter
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import com.paya.presentation.utils.loge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RiskAssessmentViewModel @Inject constructor(
    private val getRiskAssessmentQuestionsUseCase: UseCase<Unit, RiskAssessmentResponseRepoModel>,
    private val submitRiskAssessmentAnswersQuestionsUseCase: UseCase<RiskAssessmentRequestAnswerRepoBodyModel, RiskAssessmentSubmitResponseRepoModel>

) : BaseViewModel() {

    val riskAssessmentPagesLiveData = MutableLiveData<Resource<RiskAssessmentResponseRepoModel>>()

    var assessYourRiskQuestionsViewPagerCurrentPageLiveData = MutableLiveData<Int>()

    var RiskAssessmentResponseRepoModel: RiskAssessmentResponseRepoModel? = null

    var riskAssessmentRequestAnswerList: ArrayList<RiskAssessmentRequestAnswer> = arrayListOf()

    fun getRiskAssessmentQuestions() {
        viewModelScope.launch {
            //showLoading()
            val response = callResource(this@RiskAssessmentViewModel,getRiskAssessmentQuestionsUseCase.action(Unit))
            when (response.status) {
                Status.SUCCESS -> response.data?.let {
                    RiskAssessmentResponseRepoModel = it
                    loge( " riskAssessmentPages.questionCount it " + it.count   )
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

    fun getRiskAssessmentQuestionsByPageNumber(pageNumber: Int): RiskAssessmentPages? {
        return RiskAssessmentResponseRepoModel!!.pages[pageNumber - 1]
        /*return when {
            pageNumber == 0 -> {
                RiskAssessmentResponseRepoModel!!.pages.first()
            }
            pageNumber > RiskAssessmentResponseRepoModel!!.pageCount - 1 -> {
                RiskAssessmentResponseRepoModel!!.pages.last()
            }
            else -> RiskAssessmentResponseRepoModel!!.pages[pageNumber - 1]
        }*/
    }

    fun saveRiskAssessmentRequestAnswer(riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer){
        riskAssessmentRequestAnswerList.add(riskAssessmentRequestAnswer)
    }

    fun submitRiskAssessmentRequestAnswer(){
        viewModelScope.launch {
            val riskAssessmentRequestAnswerRepoBodyModel = RiskAssessmentRequestAnswerRepoBodyModel(riskAssessmentRequestAnswerList)
            submitRiskAssessmentAnswersQuestionsUseCase.action(riskAssessmentRequestAnswerRepoBodyModel)
        }

    }

    /*fun plusAssessYourRiskQuestionsViewPagerCurrentPage() {
        viewModelScope.launch {


            riskAssessmentPagesLiveData.postValue(response)
            //hideLoading()
        }
    }

    fun minusAssessYourRiskQuestionsViewPagerCurrentPage() {
        viewModelScope.launch {
            assessYourRiskQuestionsViewPagerCurrentPageLiveData.value =
                assessYourRiskQuestionsViewPagerCurrentPageLiveData.value?.plus(1)


            riskAssessmentPagesLiveData.postValue(response)
            //hideLoading()
        }
    }*/

}