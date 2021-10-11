package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.home.adapter.MarketAdapter
import com.paya.presentation.ui.riskAssessment.adapter.RiskAssessmentQuestionFragmentRecycleViewAdapter
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_question_risk_assessment.*

@AndroidEntryPoint
class RiskAssessmentQuestionFragment: BaseFragment<RiskAssessmentViewModel>() {

    private var riskAssessmentQuestionFragmentRecycleViewAdapter: RiskAssessmentQuestionFragmentRecycleViewAdapter? = null
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

        observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)

        viewModel.assessYourRiskQuestionsViewPagerCurrentPageLiveData.observe(viewLifecycleOwner, Observer {
            loge( "assessYourRiskQuestionsViewPagerCurrentPageLiveData " + it   )

        })

    }

    override fun onResume() {
        super.onResume()
        //viewModel.getRiskAssessmentQuestions()
    }

    private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                /*adapter = CreateLowRiskAccountFragmentAdapter(requireContext(),childFragmentManager, riskAssessmentResponseRemoteModel.count )
                assessYourRiskQuestionsViewPager.adapter = adapter
                assessYourRiskQuestionsViewPager.offscreenPageLimit = 2*/
                viewModel.assessYourRiskQuestionsViewPagerCurrentPageLiveData.observe(viewLifecycleOwner, Observer { pageNumber ->


                })

                val riskAssessmentQuestionRemotes = riskAssessmentResponseRemoteModel.pages[0].questions
                /*loge( " riskAssessmentPages. pageNumber " + pageNumber   )
                loge( " riskAssessmentPages.questionCount " + riskAssessmentQuestionRemotes.size   )*/
                val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                //layoutManager.reverseLayout = true
                riskAssessmentQuestionFragmentRecycleViewAdapter = RiskAssessmentQuestionFragmentRecycleViewAdapter(riskAssessmentQuestionRemotes)
                riskAssessmentQuestionFragmentRecycleViewAdapter?.let {
                    riskAssessmentQuestionFragmentRecycleView.adapter = it
                }
                riskAssessmentQuestionFragmentRecycleView.layoutManager = layoutManager


            }
            else -> return
        }
    }




    override val baseViewModel: BaseViewModel
        get() = viewModel


}

