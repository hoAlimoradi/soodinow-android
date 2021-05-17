package com.paya.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.Utils
import kotlinx.android.synthetic.main.row_market_currency.view.*

class MarketAdapter() : BaseAdapter<MarketAdapter.MarketViewHolder,CurrencyPriceRepoModel>() {
	
	override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
		val inflate = LayoutInflater.from(parent.context)
		val view = inflate.inflate(R.layout.row_market_currency,parent,false)
		return MarketViewHolder(view)
	}
	
	override fun onBindHolder(holder: MarketViewHolder,model: CurrencyPriceRepoModel,position: Int) {
		val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
		params.bottomMargin = if (data.lastIndex == position) holder.itemView.resources.getDimension(R.dimen.space_bottom_navigation).toInt() else 0
		holder.itemView.layoutParams = params
		with(holder.itemView) {
			model.price?.let {
				val currencyPrice = Utils.separatorAmount(it.toInt()).toString()
				marketWealthValue.text = currencyPrice
			}
			marketTitle.text = model.name
			val pathResourceId = when (model.changeStatus) {
				"+" -> {
					R.drawable.ic_path_up
				}
				"-" -> {
					R.drawable.ic_path_down
				}
				else -> {
					null
				}
			}
			pathResourceId?.let { marketPercentageIcon.setImageResource(it) }
			if (model.changeStatus != null && model.changePercent != null) {
				marketPercentageValue.text = "${model.changeStatus} ${model.changePercent} %"
			}
		}
	}
	
	
	class MarketViewHolder(itemView: View) : ViewHolder(itemView) {
	
	}
}