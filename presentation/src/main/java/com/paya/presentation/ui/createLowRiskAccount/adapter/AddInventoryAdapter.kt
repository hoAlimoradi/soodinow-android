package com.paya.presentation.ui.createLowRiskAccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.AddInventoryPriceRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import kotlinx.android.synthetic.main.row_add_inventory.view.*

class AddInventoryAdapter() : BaseAdapter<AddInventoryAdapter.AddInventoryAdapterViewHolder, AddInventoryPriceRepoModel>() {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): AddInventoryAdapterViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_add_inventory,parent,false)
        return AddInventoryAdapterViewHolder(view)
    }

    override fun onBindHolder(holder: AddInventoryAdapterViewHolder, model: AddInventoryPriceRepoModel, position: Int) {

        with(holder.itemView) {
            addInventoryButton.text = model.name
        }
    }

    class AddInventoryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}