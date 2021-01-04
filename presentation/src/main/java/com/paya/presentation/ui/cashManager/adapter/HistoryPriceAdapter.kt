package com.paya.presentation.ui.cashManager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.PriceModel
import com.paya.presentation.R
import kotlinx.android.synthetic.main.row_cash_history_manager.view.*

class HistoryPriceAdapter :
    PagingDataAdapter<PriceModel, HistoryPriceAdapter.HistoryPriceViewHolder>(HistoryPriceComparator) {


    override fun onBindViewHolder(holder: HistoryPriceViewHolder, position: Int) {
       with(holder.itemView) {
           valueText.text = getItem(position)?.title ?: ""
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPriceViewHolder {
        return HistoryPriceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_cash_history_manager, parent, false)
        )
    }

    class HistoryPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object HistoryPriceComparator : DiffUtil.ItemCallback<PriceModel>() {
        override fun areItemsTheSame(oldItem: PriceModel, newItem: PriceModel): Boolean {
            // Id is unique.
            return true
        }

        override fun areContentsTheSame(oldItem: PriceModel, newItem: PriceModel): Boolean {
            return true
        }
    }
}