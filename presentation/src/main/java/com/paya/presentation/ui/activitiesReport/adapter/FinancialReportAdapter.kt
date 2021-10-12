package com.paya.presentation.ui.activitiesReport.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.InvestmentHeaderDate
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.presentation.R
import com.paya.presentation.ui.activitiesReport.enum.StateInvestment
import com.paya.presentation.ui.activitiesReport.enum.TypeAccount
import com.paya.presentation.ui.activitiesReport.enum.TypeInvestment
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.getDifferenceDate
import com.paya.presentation.utils.getTimeHoursAndMinute
import kotlinx.android.synthetic.main.item_financial_report.view.*
import kotlinx.android.synthetic.main.item_financial_report_header.view.*
import java.util.*

const val HEADER = 1
const val OTHER = 0

class FinancialReportAdapter(private val onItemClicked:(InvestmentLogsModel) -> Unit) :
    PagingDataAdapter<Any, RecyclerView.ViewHolder>(FinancialPriceComparator) {


    class ActivityReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ActivityReportViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            if (viewType == HEADER)
                R.layout.item_financial_report_header
            else
                R.layout.item_financial_report
            , parent, false
        )
        return if (viewType == HEADER) ActivityReportViewHolderHeader(view) else ActivityReportViewHolder(
            view
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is InvestmentHeaderDate) HEADER else OTHER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActivityReportViewHolderHeader) {
            val item = getItem(position) as InvestmentHeaderDate
            with(holder.itemView) {
                val date = Utils.convertStringToPersianCalender(item.date)
                if (date != null) {
                    txtHeaderTitleDate.text = date.persianShortDate
                }
                txtHeaderTitleDateText.text = Date().getDifferenceDate(item.date)
            }
        } else {
            val item = getItem(position) as InvestmentLogsModel
            with(holder.itemView) {
                setOnClickListener { onItemClicked.invoke(item) }
                iconImageView.setImageResource(TypeInvestment.getTypeWithString(item.type).icon)
                txtTitlePrice.text = Utils.separatorAmount(item.startPrice)
                txtTitleTypeAccount.text = item.description
                val date = Utils.convertStringToDate(item.createdAt)
                if (date != null) {
                    txtTitleDate.text = date.getTimeHoursAndMinute()
                }
                with(errorTxt) {
                    val state = StateInvestment.getStateWithString(item.state)
                    text = (item.description + " " + state.title).trim()
                    setTextColor(ContextCompat.getColor(context, state.color))
                    setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, state.dot, 0)
                }
            }
        }
    }


    object FinancialPriceComparator : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            // Id is unique.
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return newItem == oldItem

        }
    }

}