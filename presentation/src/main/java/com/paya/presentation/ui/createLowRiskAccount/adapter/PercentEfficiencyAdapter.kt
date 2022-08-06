package com.paya.presentation.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.domain.models.repo.EfficiencyRepoModel
import com.paya.domain.models.repo.PercentEfficiency
import com.paya.presentation.R
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.roundOffDecimal
import kotlinx.android.synthetic.main.row_percent_efficiency.view.*

class PercentEfficiencyAdapter(val list: List<PercentEfficiency>) :
    RecyclerView.Adapter<PercentEfficiencyAdapter.EfficiencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EfficiencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_percent_efficiency, parent, false)
        return EfficiencyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EfficiencyViewHolder, position: Int) {
        val item = list[position]
        with(holder.itemView) {
            titleEfficiency.text = item.title
            percentEfficiency.text = if (item.percent > 0) "% ${roundOffDecimal(item.percent)}" else ""
            with(percentIconEfficiency) {
                setImageResource( R.drawable.ic_arrow_dposit)
                setBackgroundResource( R.drawable.bg_icon_15_size_corner_3_green )
            }
        }
    }

    class EfficiencyViewHolder(itemView: View) : ViewHolder(itemView)
}