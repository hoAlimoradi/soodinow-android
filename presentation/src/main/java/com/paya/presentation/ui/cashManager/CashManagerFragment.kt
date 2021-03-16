package com.paya.presentation.ui.cashManager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.BoxTypeParam
import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.TotalBoxValueRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCashManagerBinding
import com.paya.presentation.utils.Utils.getAmount
import com.paya.presentation.utils.Utils.separatorAmount
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CashManagerViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CashManagerFragment : BaseFragment<CashManagerViewModel>() {

    private val mViewModel: CashManagerViewModel by viewModels()
    private lateinit var mBinding: FragmentCashManagerBinding
    private var spinnerBoxAdapter: BoxTypeAdapter? = null
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
        observe(mViewModel.pullPriceStatus, ::readyPullPrice)
        observe(mViewModel.totalBoxValueStatus, ::readyTotalBoxValue)
        setupToggle()
        mBinding.managerAccountHistory.setOnClickListener {
            findNavController().navigate(
                R.id.navigateCashHistory
            )
        }
        mBinding.inputSelectTypes.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerBoxAdapter?.let { adapter ->
                        adapter.getItem(position)?.let {
                            mViewModel.type.set(it.type)
                            mViewModel.getSellPrice()
                        }
                    }

                }

            }

        mBinding.submitBtn.setOnClickListener {
            if (mViewModel.priceType == CashManagerViewModel.PriceType.deposit)
                mViewModel.price.set(getAmount(mBinding.inputPrice.text.toString()))
            mViewModel.setPullPrice()
        }
    }

    private fun setupToggle() {
        mBinding.depositAccountBtn.isSelected = true
        mBinding.depositAccountBtn.setOnClickListener {
            mBinding.depositAccountBtn.isSelected = true
            mBinding.withdrawalAccountBtn.isSelected = false
            mViewModel.priceType = CashManagerViewModel.PriceType.deposit
            mBinding.inputPrice.isEnabled = true
            mBinding.seekBarGroup.visibility = View.INVISIBLE
        }
        mBinding.withdrawalAccountBtn.setOnClickListener {
            mBinding.depositAccountBtn.isSelected = false
            mBinding.withdrawalAccountBtn.isSelected = true
            mViewModel.priceType = CashManagerViewModel.PriceType.withdrawal
            mBinding.inputPrice.isEnabled = false
            mBinding.seekBarGroup.visibility = View.VISIBLE
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
                spinnerBoxAdapter = resource.data?.types?.let {
                    context?.let { con ->
                        BoxTypeAdapter(
                            it.toList(),
                            con,
                            android.R.layout.simple_spinner_item,
                        )
                    }
                }
                mBinding.inputSelectTypes.adapter = spinnerBoxAdapter

            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else -> return
        }
    }

    private fun readyTotalBoxValue(resource: Resource<TotalBoxValueRepoModel>) {
        when (resource.status) {
            Status.SUCCESS -> {

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

    private fun readyPullPrice(resource: Resource<String>) {
        when (resource.status) {
            Status.SUCCESS -> {
                Toast.makeText(
                    context,
                    getString(R.string.pull_price_message_insert),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Status.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
            }
            else -> return
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

    class BoxTypeAdapter(
        private val boxTypes: List<BoxTypeParam>,
        context: Context,
        val resource: Int
    ) :
        ArrayAdapter<BoxTypeParam>(context, resource) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            val row = inflater.inflate(resource, parent, false)
            val item = boxTypes[position]
            val textView = row.findViewById(android.R.id.text1) as TextView
            textView.text = item.name
            return row
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(parent.context)
            val row = inflater.inflate(resource, parent, false) as TextView
            val item = boxTypes[position]
            val textView = row.findViewById(android.R.id.text1) as TextView
            textView.text = item.name
            return row
        }

        override fun getCount(): Int {
            return boxTypes.size
        }

        override fun getItem(position: Int): BoxTypeParam? {
            return boxTypes[position]
        }
    }
}