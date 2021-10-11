package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.SoodinowWalletContractRepoModel
import com.paya.domain.models.repo.WalletHostListRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createLowRiskAccount.adapter.*
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CreateLowRiskAccountViewModel
import com.paya.presentation.viewmodel.SoodinowWalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wallet_soodinow.*

@AndroidEntryPoint
class SoodinowWalletFragment : BaseFragment<SoodinowWalletViewModel>(), StartInvestClickListener {
    private val mViewModel: SoodinowWalletViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_soodinow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.hostsLiveData, ::onReady)
        mViewModel.hostsList()
    }

    private fun onReady(resource: Resource<List<WalletHostListRepoModel>>) {

        if (resource.status == Status.SUCCESS) {
            resource.data?.let {
                val soodinowWalletContractRepoModel = it.map {data->
                    SelectContractWalletItem(
                        data.id,
                        data.name,
                        data.descriptionTitle,
                        data.descriptionBody,
                        data.efficiency.threeMonth.percent,
                        data.efficiency.month.percent,
                        data.efficiency.week.percent,
                        context?.let { context -> ContextCompat.getDrawable(context,R.drawable.ic_image_wallet) }?:null
                    )
                }
                var list:MutableList<SelectContractWalletRecyclerViewItem> = mutableListOf(SectionItem(isFarabi = false, title = "ویژگی سودینو", description = "نقد شدن راحت و در کمترین زمان ممکن"))
                for (item in soodinowWalletContractRepoModel) {
                    list.add(item)
                }
                setupSoodinowWalletAdapter(list)
            }
        }

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

    override fun onPositionClicked(position: Int,hostId: Int, isFarabi: Boolean) {
        if (hostId==-1) {

        }else {
            getFindViewController()?.navigateUp()
            getFindViewController()?.navigate(
                R.id.openSoodinowAutomaticInvestmentAccountFragment
            )
        }
    }
}