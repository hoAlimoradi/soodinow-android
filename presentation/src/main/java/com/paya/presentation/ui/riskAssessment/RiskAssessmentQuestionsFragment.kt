package com.paya.presentation.ui.riskAssessment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.paya.domain.models.repo.RiskAssessmentResponseRepoModel
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
import kotlinx.android.synthetic.main.fragment_question_risk_assessment.*
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.*
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.assessYourRiskPercentValue
import kotlinx.android.synthetic.main.fragment_questions_risk_assessment.assessYourRiskStepperIndicator
import kotlinx.android.synthetic.main.item_wallet.view.*


@AndroidEntryPoint
class RiskAssessmentQuestionsFragment : BaseFragment<RiskAssessmentViewModel>() {

    private var viewPagerAdapter : RiskAssessmentQuestionsFragmentViewPagerAdapter? = null
    private var riskAssessmentQuestionFragments = arrayListOf<RiskAssessmentQuestionFragment>()
    private var assessYourRiskNextIsActive = false
    private val viewModel: RiskAssessmentViewModel by activityViewModels()
    var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_questions_risk_assessment, container, false)

        return view

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
        assessYourRiskStepperIndicator.progress = currentPage * (riskAssessmentQuestionFragments.size /100)
        assessYourRiskPercentValue.text = "% " + currentPage * (riskAssessmentQuestionFragments.size /100)

        assessYourRiskPrevious.setOnClickListener {

            if (currentPage == 0) {
                findNavController().popBackStack()
            } else {
                currentPage -= 1
            }
            assessYourRiskStepperIndicator.progress = currentPage * (riskAssessmentQuestionFragments.size /100)
            assessYourRiskPercentValue.text = "% " + currentPage * (riskAssessmentQuestionFragments.size /100)
        }

        assessYourRiskNext.setOnClickListener {

            if (viewModel.checkWhetherAssessYourRiskNextIsActiveOrNot(currentPage)) {
                if (currentPage < riskAssessmentQuestionFragments.size -1) {
                    assessYourRiskQuestionsViewPager.currentItem++
                    currentPage = assessYourRiskQuestionsViewPager.currentItem


                } else {
                    viewModel.submitRiskAssessmentRequestAnswer()
                    getFindViewController()?.navigateUp()
                    getFindViewController()?.navigate(R.id.riskAssessmentConfirm)
                }

                assessYourRiskStepperIndicator.progress = currentPage * (riskAssessmentQuestionFragments.size /100)
                assessYourRiskPercentValue.text = "% " + currentPage * (riskAssessmentQuestionFragments.size /100)
            } else {
                Toast.makeText(context, " لطفا به تمامی سوالات ضروری در این صفحه پاسخ دهید ", Toast.LENGTH_SHORT)
                    .show()
            }

        }


    }

    override fun onResume() {
        super.onResume()
        riskAssessmentQuestionFragments.clear()
        viewModel.getRiskAssessmentQuestions()


    }


    private fun onDataReady(resource: Resource<RiskAssessmentResponseRepoModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRepoModel ->

                val count = riskAssessmentResponseRepoModel.pageCount -1
                for (i in 0..count) {
                    riskAssessmentQuestionFragments.add(RiskAssessmentQuestionFragment.newInstance(i))
                }

                viewPagerAdapter = RiskAssessmentQuestionsFragmentViewPagerAdapter(childFragmentManager,
                    riskAssessmentQuestionFragments )

                assessYourRiskQuestionsViewPager.adapter = viewPagerAdapter
                assessYourRiskQuestionsViewPager.offscreenPageLimit = count
                assessYourRiskQuestionsViewPager.adapter?.let {
                    if (it.count > 1) {
                        assessYourRiskQuestionsViewPager.currentItem = 1
                        assessYourRiskQuestionsViewPager.currentItem = 0
                    }
                }

            }
            else -> return
        }
    }

    override fun onDestroyView() {
        forceDestroyCurrentItemInViewPager()
        super.onDestroyView()
    }

    // Call this method to call destroyItem() for current item in view pager.
    private fun forceDestroyCurrentItemInViewPager() {
        val position: Int = assessYourRiskQuestionsViewPager.currentItem
        viewPagerAdapter?.let {
            val item = it.getItem(position)
            viewPagerAdapter?.destroyItem(assessYourRiskQuestionsViewPager, position, item)
        }
        riskAssessmentQuestionFragments.clear()
        viewPagerAdapter?.notifyDataSetChanged()
        (assessYourRiskQuestionsViewPager.adapter as RiskAssessmentQuestionsFragmentViewPagerAdapter).clear()
        (assessYourRiskQuestionsViewPager.adapter as RiskAssessmentQuestionsFragmentViewPagerAdapter).notifyDataSetChanged()
        viewPagerAdapter = null

    }

    override val baseViewModel: BaseViewModel
        get() = viewModel

}



