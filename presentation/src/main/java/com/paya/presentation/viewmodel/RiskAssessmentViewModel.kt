package com.paya.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.remote.RiskAssessmentPages
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.SingleLiveEvent
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiskAssessmentViewModel @Inject constructor(
    private val getRiskAssessmentQuestionsUseCase: UseCase<Unit, RiskAssessmentResponseRemoteModel>
) : BaseViewModel() {
    val riskAssessmentPagesLiveData = MutableLiveData<Resource<RiskAssessmentResponseRemoteModel>>()
    var assessYourRiskQuestionsViewPagerCurrentPageLiveData = MutableLiveData<Int>()

    fun getRiskAssessmentQuestions() {
        viewModelScope.launch {
            //showLoading()
            val response = callResource(this@RiskAssessmentViewModel,getRiskAssessmentQuestionsUseCase.action(Unit))
            riskAssessmentPagesLiveData.postValue(response)
            //hideLoading()
        }
    }

    fun setAssessYourRiskQuestionsViewPagerCurrentPage(currentPosition: Int) {
        viewModelScope.launch {
            assessYourRiskQuestionsViewPagerCurrentPageLiveData.postValue(currentPosition)
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