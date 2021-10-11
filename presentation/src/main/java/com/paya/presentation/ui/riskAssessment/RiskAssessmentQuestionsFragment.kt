package com.paya.presentation.ui.riskAssessment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.riskAssessment.adapter.RiskAssessmentQuestionsFragmentViewPagerAdapter
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.*


@AndroidEntryPoint
class RiskAssessmentQuestionsFragment : BaseFragment<RiskAssessmentViewModel>() {

    private lateinit var viewPagerAdapter : RiskAssessmentQuestionsFragmentViewPagerAdapter
    private var riskAssessmentQuestionFragments = arrayListOf<RiskAssessmentQuestionFragment>()

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

        assessYourRiskQuestionsViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                 Log.i(""," log setAssessYourRiskQuestionsViewPagerCurrentPage " + position)
                 viewModel.setAssessYourRiskQuestionsViewPagerCurrentPage(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)
        assessYourRiskPrevious.setOnClickListener {
            if (currentPage == 0) {
                findNavController().popBackStack()
            } else {
                currentPage -= 1
            }
        }
        assessYourRiskNext.setOnClickListener {
            getFindViewController()?.navigateUp()
            getFindViewController()?.navigate(R.id.riskAssessmentConfirm)

        }
    }

    override fun onResume() {
        super.onResume()
        //viewModel.getRiskAssessmentQuestions()
    }

    private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                for (i in 1..riskAssessmentResponseRemoteModel.count) {
                    riskAssessmentQuestionFragments.add(RiskAssessmentQuestionFragment())
                }

                viewPagerAdapter = RiskAssessmentQuestionsFragmentViewPagerAdapter(requireContext(), childFragmentManager,  riskAssessmentQuestionFragments )
                assessYourRiskQuestionsViewPager.adapter = viewPagerAdapter
                assessYourRiskQuestionsViewPager.offscreenPageLimit = 2
                loge( " riskAssessmentQuestionFragments  size " + riskAssessmentQuestionFragments.size   )
                loge( " riskAssessmentPages.questionCount " + riskAssessmentResponseRemoteModel.count   )
            }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel


}



