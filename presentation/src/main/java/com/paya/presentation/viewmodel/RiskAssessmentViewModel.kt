package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.remote.RiskAssessmentPages
import com.paya.domain.models.remote.RiskAssessmentRequestAnswer
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
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
    private val getRiskAssessmentQuestionsUseCase: UseCase<Unit, RiskAssessmentResponseRemoteModel>
) : BaseViewModel() {

    val riskAssessmentPagesLiveData = MutableLiveData<Resource<RiskAssessmentResponseRemoteModel>>()

    var assessYourRiskQuestionsViewPagerCurrentPageLiveData = MutableLiveData<Int>()

    var riskAssessmentResponseRemoteModel: RiskAssessmentResponseRemoteModel? = null

    var riskAssessmentRequestAnswerList: ArrayList<RiskAssessmentRequestAnswer> = arrayListOf()

    fun getRiskAssessmentQuestions() {
        viewModelScope.launch {
            //showLoading()
            val response = callResource(this@RiskAssessmentViewModel,getRiskAssessmentQuestionsUseCase.action(Unit))
            when (response.status) {
                Status.SUCCESS -> response.data?.let {


                    riskAssessmentResponseRemoteModel = it
                    loge( " riskAssessmentPages.questionCount it " + it.count   )
                }

                else -> {
                    loge( " riskAssessmentResponseRemoteModel is null " )
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
        return riskAssessmentResponseRemoteModel!!.pages[pageNumber - 1]
        /*return when {
            pageNumber == 0 -> {
                riskAssessmentResponseRemoteModel!!.pages.first()
            }
            pageNumber > riskAssessmentResponseRemoteModel!!.pageCount - 1 -> {
                riskAssessmentResponseRemoteModel!!.pages.last()
            }
            else -> riskAssessmentResponseRemoteModel!!.pages[pageNumber - 1]
        }*/
    }

    fun saveRiskAssessmentRequestAnswer(riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer){
        riskAssessmentRequestAnswerList.add(riskAssessmentRequestAnswer)
    }

    fun submitRiskAssessmentRequestAnswer(){

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