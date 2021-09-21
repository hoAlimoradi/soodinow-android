package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.adapter.CreateLowRiskAccountFragmentAdapter
import com.paya.presentation.ui.riskAssessment.adapter.RiskAssessmentQuestionsFragmentAdapter
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_connect_low_risk_brokerage.*
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.*
import kotlinx.android.synthetic.main.fragment_start_risk_assessment.*

@AndroidEntryPoint
class RiskAssessmentQuestionsFragment : BaseFragment<RiskAssessmentViewModel>() {

    private lateinit var adapter : RiskAssessmentQuestionsFragmentAdapter
    private var riskAssessmentQuestionFragments: List<RiskAssessmentQuestionFragment> = mutableListOf()

    private val viewModel: RiskAssessmentViewModel by viewModels()
    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_questions_risk_assessment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)
        assessYourRiskPrevious.setOnClickListener {
            if (currentPage == 0) {
                findNavController().popBackStack()
            } else {
                currentPage -= 1
            }

        }
        assessYourRiskNext.setOnClickListener {
            if (currentPage == 0) {
                findNavController().popBackStack()
            } else {
                currentPage -= 1
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRiskAssessmentQuestions()
    }

    private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                for (i in 1..riskAssessmentResponseRemoteModel.count) {
                    riskAssessmentQuestionFragments.plus(RiskAssessmentQuestionFragment())
                }

                adapter = RiskAssessmentQuestionsFragmentAdapter(requireContext(), childFragmentManager,  riskAssessmentQuestionFragments )
                assessYourRiskQuestionsViewPager.adapter = adapter
                assessYourRiskQuestionsViewPager.offscreenPageLimit = 2
                loge( " riskAssessmentPages.questionCount " + riskAssessmentResponseRemoteModel.count   )
            }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel


}



