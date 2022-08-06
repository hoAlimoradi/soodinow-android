package com.paya.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.domain.models.repo.Currency
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.getColorByResId
import com.paya.presentation.utils.roundOffDecimal
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

			marketTitle.text = model.name
/*			marketWealthCurrencyValue.text = model.currency.persianTitle
			marketWealthCurrencyValue.visibility =
				if (model.currency == Currency.Bourse) View.INVISIBLE else View.VISIBLE*/

			val pathResourceId = when (model.changeStatus) {
				"+" -> {
					R.drawable.ic_green_arrow_drop_up

				}
				"-" -> {
					R.drawable.ic_red_arrow_drop_down
				}
				else -> {
					R.drawable.ic_green_arrow_drop_up
				}
			}
			pathResourceId.let { marketPercentageIcon.setImageResource(it) }


			if (model.changeStatus != null && model.changePercent != null) {
				marketPercentageValue.run {
					this.text =
						" %${model.changeStatus} ${model.changePercent?.let { roundOffDecimal(if (it < 0) it * -1f else it) }}"
					when (model.changeStatus) {

						"-" -> {
							this.setTextColor(this.context.getColorByResId(R.color.red))
						}

						else -> {
							this.setTextColor(this.context.getColorByResId(R.color.bg_green))
						}

					}
				}

			}


			if (model.changeStatus != null && model.price != null) {
				marketWealthValue.run {
					model.price?.let {
						val currencyPrice =
							if (model.currency == Currency.Rial) Utils.separatorAmount(it.toLong()).toString() else it.toString()
						this.text = currencyPrice
					}

					when (model.changeStatus) {

						"-" -> {
							this.setTextColor(this.context.getColorByResId(R.color.red))
						}

						else -> {
							this.setTextColor(this.context.getColorByResId(R.color.bg_green))
						}

					}
				}

			}


		}
	}
	
	
	class MarketViewHolder(itemView: View) : ViewHolder(itemView) {
	
	}
}