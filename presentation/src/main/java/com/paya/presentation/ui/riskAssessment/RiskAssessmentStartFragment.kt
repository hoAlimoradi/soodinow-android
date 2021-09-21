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
import com.paya.presentation.utils.loge
import com.paya.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_start_risk_assessment.*

@AndroidEntryPoint
class RiskAssessmentStartFragment : BaseFragment<RiskAssessmentViewModel>() {

    private val viewModel: RiskAssessmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_risk_assessment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assessYourRiskStartAtAnotherTime.setOnClickListener {
            findNavController().popBackStack()
        }
        observe(viewModel.riskAssessmentPagesLiveData, ::onDataReady)


    }

    override fun onResume() {
        super.onResume()
        viewModel.getRiskAssessmentQuestions()
    }

    private fun onDataReady(resource: Resource<RiskAssessmentResponseRemoteModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                loge( " riskAssessmentPages.questionCount " + riskAssessmentResponseRemoteModel.count   )
            }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel


}


