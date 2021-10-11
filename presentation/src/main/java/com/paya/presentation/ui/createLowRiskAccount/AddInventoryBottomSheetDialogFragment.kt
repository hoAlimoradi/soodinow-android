package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paya.domain.models.repo.AddInventoryPriceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.ui.createLowRiskAccount.adapter.AddInventoryAdapter
import com.paya.presentation.viewmodel.ConnectLowRiskBrokerageViewModel
import kotlinx.android.synthetic.main.layout_add_inventory_modal_bottom_sheet.*

//@AndroidEntryPoint
class AddInventoryBottomSheetDialogFragment: BottomSheetDialogFragment(){

    companion object {
        const val TAG = "AddInventoryBottomSheetDialogFragment"
    }
    private val viewModel: ConnectLowRiskBrokerageViewModel by viewModels()

    private var addInventoryAdapter: AddInventoryAdapter? = null

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
        val layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.VERTICAL,
            true
        )
        //layoutManager.reverseLayout = true
        addInventoryRecyclerView.layoutManager = layoutManager
        addInventoryAdapter = AddInventoryAdapter()
        onAddInventoryPriceReady(getAddInventoryPriceList())
        addInventoryAdapter?.let {
            addInventoryRecyclerView.adapter = it
        }

        amountRequiredValueConstraintLayout.setOnClickListener {


        }
        nextButton.setOnClickListener {


        }


    }

    private fun onAddInventoryPriceReady(markets: ArrayList<AddInventoryPriceRepoModel>){
        addInventoryAdapter?.let {
            it.clear()
            it.addAllData(markets )
        }
    }

    private fun getAddInventoryPriceList(): ArrayList<AddInventoryPriceRepoModel> {
        val first = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val second = AddInventoryPriceRepoModel(name = "250 تومان", price = 250F)
        val third = AddInventoryPriceRepoModel(name = "500 تومان", price = 500F)
        val fourth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val fifth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        val sixth = AddInventoryPriceRepoModel(name = "100 تومان", price = 100F)
        var list  = ArrayList<AddInventoryPriceRepoModel>()
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

