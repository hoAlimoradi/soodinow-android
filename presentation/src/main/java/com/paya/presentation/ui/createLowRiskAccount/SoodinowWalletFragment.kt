package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.tools.Resource
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.adapter.*
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_soodinow.*

@AndroidEntryPoint
class SoodinowWalletFragment : BaseFragment<CreateLowRiskAccountViewModel>(), StartInvestClickListener {
    private val mViewModel: CreateLowRiskAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_soodinow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.soodinowWalletRepoModelResource, ::onReady)
        mViewModel.getSoodinowWalletContracts()
    }

    private fun onReady(resource: Resource<List<SoodinowWalletContractRepoModel>>) {

        /*todo var list: List<SoodinowWalletRecyclerViewItem> = listOf(SectionItem(title = "firstItem"))

        if (!resource.data.isNullOrEmpty()) {
            resource.data?.forEach {

                list.plus(
                    SoodinowWalletItem(
                        pointTitle = it.pointTitle,
                        name = it.name,
                        description = it.description,
                        trimesterValue = it.trimesterValue,
                        monthlyValue = it.monthlyValue,
                        weeklyValue = it.weeklyValue
                    )
                )
            }
            setupSoodinowWalletAdapter(list)
        }*/

        val soodinowWalletContractRepoModel = SelectContractWalletItem(
            pointTitle = "پیمان درآمد ثابت(پر ریسک )",
            name = "ترکیب صندوق سرمایه گزاری سودینو",
            description = "سودینو متشکل از چندین خدمت گوناگون متناسب با نیاز های مختلف سرمایه گذاران می باشد که تمام تمرکز سودینو انجام این خدمات به بهترین شکل می باشد تا منافع ناشی از آن سبب بهبود زندگی افراد جامعه باشد",
            trimesterValue =  62 ,
            monthlyValue =  21,
            weeklyValue =  5
        )
        var list: List<SelectContractWalletRecyclerViewItem> = listOf(SectionItem(isFarabi = false, title = "ویژگی سودینو", description = "نقد شدن راحت و در کمترین زمان ممکن"), soodinowWalletContractRepoModel)
        setupSoodinowWalletAdapter(list)
    }


    private fun setupSoodinowWalletAdapter(list: List<SelectContractWalletRecyclerViewItem>) {
        context?.let { context ->
            soodinowWalletRecycleView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SelectContractWalletAdapter(this@SoodinowWalletFragment, list)
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