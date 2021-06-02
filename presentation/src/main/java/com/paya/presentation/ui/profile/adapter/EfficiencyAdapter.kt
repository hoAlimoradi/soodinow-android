package com.paya.presentation.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.domain.models.repo.EfficiencyRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.roundOffDecimal
import kotlinx.android.synthetic.main.row_efficiency.view.*

class EfficiencyAdapter(val list: List<EfficiencyRepoModel>) :
    RecyclerView.Adapter<EfficiencyAdapter.EfficiencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EfficiencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_efficiency, parent, false)
        return EfficiencyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EfficiencyViewHolder, position: Int) {
        val item = list[position]
        with(holder.itemView) {
            titleEfficiency.text = item.title
            percentEfficiency.text = "${roundOffDecimal(item.percent)}%"
            percentEfficiency.setTextColor(if(item.isProfitable) ContextCompat.getColor(context,R.color.green) else ContextCompat.getColor(context,R.color.red))
            priceEfficiency.text = Utils.separatorAmount(item.price.toLong())
            with(percentIconEfficiency) {
                setImageResource(if(item.isProfitable) R.drawable.ic_arrow_dposit else R.drawable.ic_arrow_withdrawal)
                setBackgroundResource(if(item.isProfitable) R.drawable.bg_icon_15_size_corner_3_green else R.drawable.bg_icon_15_size_corner_3_red)
            }
        }
    }

    class EfficiencyViewHolder(itemView: View) : ViewHolder(itemView)
}