package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.tools.Resource
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.adapter.*
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.openUrl
import com.paya.presentation.utils.setArrayStringText
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_farabi.*
import kotlinx.android.synthetic.main.fragment_wallet_soodinow.*

@AndroidEntryPoint
class FarabiWalletFragment: BaseFragment<CreateLowRiskAccountViewModel>(),
    StartInvestClickListener {

    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_farabi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.farabiWalletRepoModelResource, ::onReady)
        mViewModel.getFarabiWalletContracts()
    }

    private fun onReady(resource: Resource<List<SoodinowWalletContractRepoModel>>) {

        val soodinowWalletContractRepoModel = SelectContractWalletItem(
            pointTitle = "پیمان درآمد ثابت(پر ریسک )",
            name = "ترکیب صندوق سرمایه گزاری فارابی",
            description = "فارابی متشکل از چندین خدمت گوناگون متناسب با نیاز های مختلف سرمایه گذاران می باشد که تمام تمرکز فارابی انجام این خدمات به بهترین شکل می باشد تا منافع ناشی از آن سبب بهبود زندگی افراد جامعه باشد",
            trimesterValue =  62 ,
            monthlyValue =  21,
            weeklyValue =  5
        )
        var list: List<SelectContractWalletRecyclerViewItem> = listOf(SectionItem(isFarabi = true, title = "ویژگی فارابی", description = "استفاده از سرمایه گذاری در کارگزاری"), soodinowWalletContractRepoModel)
        setupSoodinowWalletAdapter(list)
    }


    private fun setupSoodinowWalletAdapter(list: List<SelectContractWalletRecyclerViewItem>) {
        context?.let { context ->
            farabiWalletRecycleView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SelectContractWalletAdapter(this@FarabiWalletFragment, list)
            }
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

    override fun onPositionClicked(position: Int, isFarabi: Boolean) {
        getFindViewController()?.navigate(
            R.id.openSoodinowAutomaticInvestmentAccountFragment
        )
    }
}