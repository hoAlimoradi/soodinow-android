package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.remote.RiskAssessmentRequestAnswer
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.riskAssessment.adapter.RiskAssessmentQuestionFragmentRecycleViewAdapter
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_question_risk_assessment.*

private const val ARG_PARAM_PAGE_NUMBER = "param_page_number"
@AndroidEntryPoint
class RiskAssessmentQuestionFragment: BaseFragment<RiskAssessmentViewModel>() ,
    RiskAssessmentQuestionFragmentRecycleViewAdapter.QuestionCallback {

    private var riskAssessmentQuestionFragmentRecycleViewAdapter: RiskAssessmentQuestionFragmentRecycleViewAdapter? = null
   // private val viewModel: RiskAssessmentViewModel by viewModels() viewModels({requireParentFragment()})
    private val viewModel: RiskAssessmentViewModel by activityViewModels()
    private var paramPageNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramPageNumber = it.getInt(ARG_PARAM_PAGE_NUMBER, 0)
            loge( "paramPageNumber " + paramPageNumber   )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question_risk_assessment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)


        viewModel.assessYourRiskQuestionsViewPagerCurrentPageLiveData.observe(viewLifecycleOwner, Observer { pageNumber ->
            loge( " صفحه چاری " + pageNumber   )

            //riskAssessmentResponseRemoteModel.pages[0].questions
            /*loge( " riskAssessmentPages. pageNumber " + pageNumber   )
            loge( " riskAssessmentPages.questionCount " + riskAssessmentQuestionRemotes.size   )*/

            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            //layoutManager.reverseLayout = true
            viewModel.getRiskAssessmentQuestionsByPageNumber(pageNumber)?.let {  riskAssessmentQuestionRemotes ->
                riskAssessmentQuestionFragmentRecycleViewAdapter = RiskAssessmentQuestionFragmentRecycleViewAdapter(this@RiskAssessmentQuestionFragment, riskAssessmentQuestionRemotes.questions)
                riskAssessmentQuestionFragmentRecycleViewAdapter?.let {
                    riskAssessmentQuestionFragmentRecycleView.adapter = it
                }
                riskAssessmentQuestionFragmentRecycleView.layoutManager = layoutManager
            }


        })


    }

    override fun onResume() {
        super.onResume()

    }


    override val baseViewModel: BaseViewModel
        get() = viewModel

    companion object {
        @JvmStatic
        fun newInstance(paramPageNumber: Int) =
            RiskAssessmentQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM_PAGE_NUMBER, paramPageNumber)
                }
            }
    }

    override fun onQuestionClicked(
        riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer
    ) {
        viewModel.saveRiskAssessmentRequestAnswer(riskAssessmentRequestAnswer)
    }

}

