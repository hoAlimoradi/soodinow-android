package com.paya.presentation.ui.whySoodinow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.domain.models.repo.WhySoodinowModel
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.roundOffDecimal
import kotlinx.android.synthetic.main.row_market_currency.view.*
import kotlinx.android.synthetic.main.row_why_soodinow.view.*

class WhySoodinowAdapter() : BaseAdapter<WhySoodinowAdapter.MarketViewHolder, WhySoodinowModel>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_why_soodinow,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder,model: WhySoodinowModel, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = if (data.lastIndex == position) holder.itemView.resources.getDimension(R.dimen.space_bottom_navigation).toInt() else 0
        holder.itemView.layoutParams = params
        with(holder.itemView) {
            whySoodinowTitle.text = model.title
            whySoodinowDesc.text = model.description
        }
    }


    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}


