package com.paya.presentation.ui.createLowRiskAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paya.domain.models.repo.AddInventoryPriceRepoModel
import com.paya.presentation.R
import com.paya.presentation.ui.createLowRiskAccount.adapter.AddInventoryAdapter
import com.paya.presentation.utils.NumberTextWatcher
import com.paya.presentation.utils.Utils
import com.paya.presentation.viewmodel.ConnectLowRiskBrokerageViewModel
import kotlinx.android.synthetic.main.layout_add_inventory_modal_bottom_sheet.*

//@AndroidEntryPoint
class AddInventoryBottomSheetDialogFragment: BottomSheetDialogFragment(){

    companion object {
        const val TAG = "AddInventoryBottomSheetDialogFragment"
    }
    private val viewModel: ConnectLowRiskBrokerageViewModel by viewModels()

    private var addInventoryAdapter: AddInventoryAdapter? = null

     var onClick: ((Long) -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.layout_add_inventory_modal_bottom_sheet, container, false)
       // viewModel.getAddInventoryPriceList()
       // observe(viewModel.addInventoryPriceListLiveData, ::onAddInventoryPriceReady)
        //view.setBackgroundResource(R.drawable.bg_chart_line_gray);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let { (it.parent as View).setBackgroundColor(Color.TRANSPARENT) }
        val watcher = NumberTextWatcher(
            amountRequiredValue,
            ",###",
            lifecycleScope
        ) { }

        amountRequiredValue.addTextChangedListener(watcher)
        val layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.VERTICAL,
            true
        )
        //layoutManager.reverseLayout = true
        addInventoryRecyclerView.layoutManager = layoutManager
        addInventoryAdapter = AddInventoryAdapter { price ->
            amountRequiredValue.setText(price.toString())
        }
        onAddInventoryPriceReady(getAddInventoryPriceList())
        addInventoryAdapter?.let {
            addInventoryRecyclerView.adapter = it
        }

        nextButton.setOnClickListener {
            if (amountRequiredValue.text.toString().isEmpty())
                return@setOnClickListener
            val price = Utils.getAmount(amountRequiredValue.text.toString()) ?: 0L
            if (price == 0L)
                return@setOnClickListener
            onClick?.let { onClick -> onClick.invoke(price) }

        }


    }

    private fun onAddInventoryPriceReady(markets: ArrayList<AddInventoryPriceRepoModel>){
        addInventoryAdapter?.let {
            it.clear()
            it.addAllData(markets )
        }
    }

    private fun getAddInventoryPriceList(): ArrayList<AddInventoryPriceRepoModel> {
        val first = AddInventoryPriceRepoModel(name = "100,000 ریال", price = 100000L)
        val second = AddInventoryPriceRepoModel(name = "500,000 ریال", price = 500000L)
        val third = AddInventoryPriceRepoModel(name = "1,000,000 ریال", price = 1000000L)
        val fourth = AddInventoryPriceRepoModel(name = "2,000,000 ریال", price = 2000000L)
        val fifth = AddInventoryPriceRepoModel(name = "3,000,000 ریال", price = 3000000L)
        val sixth = AddInventoryPriceRepoModel(name = "5,000,000 ریال", price = 5000000L)
        var list = ArrayList<AddInventoryPriceRepoModel>()
        list.add(first)
        list.add(second)
        list.add(third)
        list.add(fourth)
        list.add(fifth)
        list.add(sixth)
        list.reverse()
        return list
    }


}

