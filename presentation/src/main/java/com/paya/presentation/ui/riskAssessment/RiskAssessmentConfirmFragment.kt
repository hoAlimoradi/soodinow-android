package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.HOST_ID
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_confirm_risk_assessment.*

@AndroidEntryPoint
class RiskAssessmentConfirmFragment : BaseFragment<RiskAssessmentViewModel>() {

    private val viewModel: RiskAssessmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_risk_assessment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assessYourRiskStartNow.setOnClickListener {
            getFindViewController()?.navigateUp()
            var bundle = Bundle()
            bundle.putInt(HOST_ID,9)
            getFindViewController()?.navigate(
                R.id.openSoodinowAutomaticInvestmentAccountFragment,
                bundle
            )
        }
        assessYourRiskIWillTryAgain.setOnClickListener {
            findNavController().popBackStack()
        }
       // observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)
        finalAssessYourRiskProgressView.setProgress(0.5f)
    }


    override fun onResume() {
        super.onResume()

        loge( "  بینمی " + viewModel.toString()   )
       // viewModel.getRiskAssessmentQuestions()
    }

    /*private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                loge( " riskAssessmentPages.questionCount " + riskAssessmentResponseRemoteModel.count   )
            }
            else -> return
        }
    }*/

    override val baseViewModel: BaseViewModel
        get() = viewModel


}