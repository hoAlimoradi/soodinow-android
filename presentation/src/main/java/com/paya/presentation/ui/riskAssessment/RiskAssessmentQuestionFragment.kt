package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RiskAssessmentQuestionFragment: BaseFragment<RiskAssessmentViewModel>() {

    private val viewModel: RiskAssessmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question_risk_assessment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRiskAssessmentQuestions()
    }

    /*private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->
                adapter = CreateLowRiskAccountFragmentAdapter(requireContext(),childFragmentManager, riskAssessmentResponseRemoteModel.count )
                assessYourRiskQuestionsViewPager.adapter = adapter
                assessYourRiskQuestionsViewPager.offscreenPageLimit = 2
                loge( " riskAssessmentPages.questionCount " + riskAssessmentResponseRemoteModel.count   )
            }
            else -> return
        }
    }*/

    override val baseViewModel: BaseViewModel
        get() = viewModel


}

