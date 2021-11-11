package com.paya.presentation.ui.riskAssessment

import com.paya.presentation.viewmodel.RiskAssessmentViewModel
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.remote.RiskAssessmentResponseRemoteModel
import com.paya.domain.models.repo.RiskAssessmentSubmitResponseRepoModel
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

    private var assessYourRiskStartNowEnable = false
    private var hostId = 0
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
            if (assessYourRiskStartNowEnable) {
                getFindViewController()?.navigateUp()
                val bundle = Bundle()
                bundle.putInt(HOST_ID,9)
                getFindViewController()?.navigate(
                    R.id.openSoodinowAutomaticInvestmentAccountFragment,
                    bundle
                )
            } else {
                Toast.makeText(context, "محاسبه ریسک سرمایه گذاری دچار مشکل شده ", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        assessYourRiskIWillTryAgain.setOnClickListener {
            viewModel.clearQuestions()
            viewModel.clearQuestionsMutableLiveData.observe(viewLifecycleOwner, Observer {
                if (it) {
                    findNavController().popBackStack()
                }
            })
        }

        observe(viewModel.riskAssessmentSubmitLiveData, ::onDataReady)

    }


    override fun onResume() {
        super.onResume()

        loge( "  بینمی " + viewModel.toString()   )
       // viewModel.getRiskAssessmentQuestions()
    }

    private fun onDataReady(resource: Resource<RiskAssessmentSubmitResponseRepoModel>){

        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { riskAssessmentResponseRemoteModel ->

                // {"data":{"result_id":7,"risk_value":51.01,"investment_host_id":7,"error_messages":[],"warning_messages":[]},"error":{"message":"انجام شد","code":200,"extra":null}}
                val risk = (riskAssessmentResponseRemoteModel.riskValue/100).round(2)
                loge(" (riskAssessmentResponseRemoteModel.riskValue.toInt()/100)"  + (riskAssessmentResponseRemoteModel.riskValue/100))
                finalAssessYourRiskProgressView.setProgress(risk)
                assessYourRiskStartNowEnable = true
                hostId = riskAssessmentResponseRemoteModel.investmentHostId
            }
            else -> {
                finalAssessYourRiskProgressView.setProgress(0f)
                assessYourRiskStartNowEnable = false
                return
            }
        }
    }
    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()

    override val baseViewModel: BaseViewModel
        get() = viewModel


}