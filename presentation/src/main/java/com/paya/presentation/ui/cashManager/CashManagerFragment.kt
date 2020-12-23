package com.paya.presentation.ui.cashManager

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.PricingCash
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCashManagerBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CashManagerViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculate_profit_capital.*

@AndroidEntryPoint
class CashManagerFragment : BaseFragment<CashManagerViewModel>() {

    private val mViewModel: CashManagerViewModel by viewModels()
    private lateinit var mBinding: FragmentCashManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cash_manager, container, false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.boxTypesStatus, ::readyBoxTypes)
        observe(mViewModel.sellPriceStatus, ::readySellPrice)
        observe(mViewModel.pullPriceStatus,::readyPullPrice)
        setupToggle()
        mBinding.inputSelectTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               mViewModel.type.set(mBinding.inputSelectTypes.selectedItem.toString())
                mViewModel.getSellPrice()
            }

        }

    }

    private fun setupToggle() {
        mBinding.depositAccountBtn.isSelected = true
        mBinding.depositAccountBtn.setOnClickListener {
            mBinding.depositAccountBtn.isSelected = true
            mBinding.withdrawalAccountBtn.isSelected = false
        }
        mBinding.withdrawalAccountBtn.setOnClickListener {
            mBinding.depositAccountBtn.isSelected = false
            mBinding.withdrawalAccountBtn.isSelected = true
        }
    }

    private fun seekBarMultiType(
        priceCashList: List<Long>
    ) {
        mBinding.seekBarPrice.isEnabled = priceCashList.size != 0

        mBinding.seekBarPrice.min = mViewModel.minSeek.get()?.toFloat()!!
        mBinding.seekBarPrice.max = mViewModel.maxSeek.get()?.toFloat()!!

        mBinding.seekBarPrice.tickCount = priceCashList.size ?: 0
        mBinding.seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
                seekParams?.let { param ->
                    mViewModel.price.set(
                        priceCashList[param.thumbPosition]
                    )
                }

            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

        }
    }

    private fun readyBoxTypes(resource: Resource<BoxTypeRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {
                mBinding.inputSelectTypes.adapter =
                    resource.data?.types?.let {
                        context?.let { con ->
                            ArrayAdapter<String>(
                                con, android.R.layout.simple_spinner_item,
                                it.toList()
                            )
                        }
                    }
            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else -> return
        }
    }

    private fun readySellPrice(resource: Resource<List<Long>>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let { seekBarMultiType(it) }
            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
            }
            else -> return
        }
    }

    private fun readyPullPrice(resource: Resource<Unit>) {
        when (resource.status) {
            Status.SUCCESS -> {
                Toast.makeText(context, getString(R.string.pull_price_message_insert), Toast.LENGTH_SHORT).show()
            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
            }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel


}