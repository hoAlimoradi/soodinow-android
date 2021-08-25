package com.paya.presentation.ui.createLowRiskAccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.utils.getColorByResId
import com.paya.presentation.utils.getDrawableByResId
import kotlinx.android.synthetic.main.row_wallet_soodinow.view.*


const val VIEW_TYPE_SECTION = 1
const val VIEW_TYPE_ITEM = 2

class SoodinowWalletAdapter(val data: List<SoodinowWalletRecyclerViewItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   /* override fun getItemViewType(position: Int): Int {
        if (data[position] is SectionItem) {
            return VIEW_TYPE_SECTION
        }
        return VIEW_TYPE_ITEM
    }*/

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if (position == 0) {
            return VIEW_TYPE_SECTION
        }
        return VIEW_TYPE_ITEM

    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*if (viewType == VIEW_TYPE_SECTION) {
            return FirstSectionSoodinowWalletAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_soodinow_title, parent, false)
            )
        }
        return SecondSectionSoodinowWalletAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_soodinow, parent, false)
        )*/

        if (viewType == VIEW_TYPE_SECTION) {
            return FirstSectionSoodinowWalletAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_soodinow_title, parent, false)
            )
        }
        return SecondSectionSoodinowWalletAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_soodinow, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is FirstSectionSoodinowWalletAdapterViewHolder) {
            holder.bind()

        }
        if (holder is SecondSectionSoodinowWalletAdapterViewHolder && item is SoodinowWalletItem) {
            holder.bind(item)
        }
    }

    internal inner class FirstSectionSoodinowWalletAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }
    }

    internal inner class SecondSectionSoodinowWalletAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SoodinowWalletItem) {
            itemView.soodinowPointTitleInCard.text = item.pointTitle
            itemView.soodinowPointName.text = item.name
            itemView.soodinowPointDescription.text = item.description

            /**
            weekly
            */
            itemView.weeklyPercentValue.run {
                if (item.weeklyValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.weeklyValue.toString()
            }
            itemView.weeklyPercentImage.run {
                if (item.weeklyValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }

            /**
            monthly
             */
            itemView.monthlyPercentValue.run {
                if (item.monthlyValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.monthlyValue.toString()
            }
            itemView.monthlyPercentImage.run {
                if (item.monthlyValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }

            /**
            trimester
             */
            itemView.trimesterText.run {
                if (item.trimesterValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.trimesterValue.toString()
            }
            itemView.trimesterPercentValue.run {
                if (item.trimesterValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }


        }
    }
}

open class SoodinowWalletRecyclerViewItem
class SectionItem(val title: String) : SoodinowWalletRecyclerViewItem()
class SoodinowWalletItem(val pointTitle: String,
                         val name: String,
                         val description: String,
                         val trimesterValue: Int,
                         val monthlyValue: Int,
                         val weeklyValue: Int) : SoodinowWalletRecyclerViewItem()