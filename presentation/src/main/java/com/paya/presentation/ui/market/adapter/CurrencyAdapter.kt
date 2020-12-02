package com.paya.presentation.ui.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.Utils

class CurrencyAdapter(
	private val prices: List<CurrencyPriceRepoModel>
) : RecyclerView.Adapter<CurrencyAdapter.StockViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StockViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_currency_detail,parent,false)
		return StockViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: StockViewHolder,position: Int) {
		val model = prices[position]
		
		holder.apply {
			model.price?.let {
				val currencyPrice = Utils.separatorAmount(it.toInt()).toString()
				price.text = currencyPrice
			}
			name.text = model.name
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
			pathResourceId?.let { changeIcon.setImageResource(it) }
			if (model.changeStatus != null && model.changePercent != null) {
				changePercent.text = "${model.changeStatus} ${model.changePercent}"
			}
		}
	}
	
	override fun getItemCount(): Int {
		return prices.size
	}
	
	class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		val price: TextView = itemView.findViewById(R.id.price)
		val name: TextView = itemView.findViewById(R.id.name)
		val changeUnit: TextView = itemView.findViewById(R.id.change_unit)
		val changePercent: TextView = itemView.findViewById(R.id.change_percent)
		val changeIcon: ImageView = itemView.findViewById(R.id.icon)
	}
	
}