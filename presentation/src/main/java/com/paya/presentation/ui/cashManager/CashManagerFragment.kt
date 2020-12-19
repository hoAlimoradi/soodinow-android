package com.paya.presentation.ui.cashManager

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import com.paya.domain.models.repo.PricingCash
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCashManagerBinding
import com.paya.presentation.utils.Utils
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
        setupToggle()
        seekBarMultiType()
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
    ) {

        mBinding.seekBarPrice.min = mViewModel.minSeek.get()?.toFloat()!!
        mBinding.seekBarPrice.max = mViewModel.maxSeek.get()?.toFloat()!!

        mBinding.seekBarPrice.tickCount = mViewModel.priceCashList.get()?.size ?: 0
        mBinding.seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
                seekParams?.let { param ->
                    mViewModel.price.set(
                        mViewModel.priceCashList.get()?.get(param.thumbPosition)?.let {
                            Utils.separatorAmount(
                                it
                            )
                        })
                }

            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel


}