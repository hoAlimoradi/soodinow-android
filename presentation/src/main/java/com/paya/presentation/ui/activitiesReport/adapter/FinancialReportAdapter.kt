package com.paya.presentation.ui.activitiesReport.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.Utils
import kotlinx.android.synthetic.main.item_financial_report.view.*

class FinancialReportAdapter() :
	RecyclerView.Adapter<FinancialReportAdapter.ActivityReportViewHolder>() {

	private val list = mutableListOf<InvestmentLogsRepoModel>()

	class ActivityReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityReportViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_financial_report, parent, false)
		return ActivityReportViewHolder(
			view
		)
	}

	override fun onBindViewHolder(holder: ActivityReportViewHolder, position: Int) {
		val item = list[position]
		with(holder.itemView) {
			iconImageView.setImageResource(
				when (item.type) {
					TypeInvestment.Reduction.name -> R.drawable.ic_arrow_withdrawal
					else -> R.drawable.ic_arrow_dposit
				}
			)
			txtTitlePrice.text = Utils.separatorAmount(item.startPrice)
			txtTitleNumber.text = item.trackingNumber
			val date = Utils.convertStringToPersianCalender(item.createdAt)
			if (date != null) {
				txtTitleDate.text = date.persianShortDate.replace(" ","\n")
			}
			with(errorTxt) {
				val errorText: String
				val colorText: Int
				val dot: Int
				when(item.state) {
					StateInvestmen.Completed.name -> {
						 errorText = "تراکنش شما با موفقیت انجام شد"
						 colorText =  ContextCompat.getColor(context,R.color.green)
						 dot = R.drawable.dot_green
					}
					StateInvestmen.Error.name -> {
						 errorText = "تراکنش شما با موفقیت انجام شد"
						 colorText =  ContextCompat.getColor(context,R.color.green)
						 dot = R.drawable.dot_green
					}
					else -> {
						 errorText = "تراکنش شما ناموفق می باشد"
						 colorText =  ContextCompat.getColor(context,R.color.red)
						 dot = R.drawable.dot_red
					}
				}
				text = errorText
				setTextColor(colorText)
				setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,dot,0)
			}
		}
	}

	override fun getItemCount(): Int {
		return list.size
	}

	fun setItems(list: List<InvestmentLogsRepoModel>) {
		this.list.addAll(list)
		notifyDataSetChanged()
	}

	enum class TypeInvestment {
		Open,
		Increase,
		Reduction
	}

	enum class StateInvestmen {
		Pending,
		Running,
		Completed,
		Error
	}

}