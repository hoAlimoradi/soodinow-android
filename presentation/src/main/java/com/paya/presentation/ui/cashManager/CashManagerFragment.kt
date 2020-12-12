package com.paya.presentation.ui.cashManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentCashManagerBinding
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_calculate_profit_capital.*


class CashManagerFragment : Fragment() {


    private lateinit var mBinding : FragmentCashManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_cash_manager, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSeekBar()
    }


    private fun setupSeekBar() {
        seekBarPrice.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
                if (seekParams != null) {
                    inputPrice.setText(seekParams.progress.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {

            }

        }
    }


}