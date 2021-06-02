package com.paya.presentation.ui.adapter.chartLablel

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R

class ChartLabelAdapter(
    private val chartLabelModels: List<ChartLabelModel>,
    private val onItemClicked: (index: Int) -> Unit
) : RecyclerView.Adapter<ChartLabelAdapter.ChartLabelViewHolder>() {
    class ChartLabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTxt: TextView = itemView.findViewById(R.id.label_txt)
        val labelColor: View = itemView.findViewById(R.id.label_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartLabelViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_chart_label, parent, false)
        return ChartLabelViewHolder(view)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ChartLabelViewHolder, position: Int) {
        val chartLabel = chartLabelModels[position]
        holder.labelTxt.text = chartLabel.labelName
        val drawable = DrawableCompat.wrap(
            ContextCompat.getDrawable(
                holder.labelColor.context,
                R.drawable.round_small_radius_corner_layout
            )!!
        )
        if (chartLabel.labelColor.isNotEmpty())
            DrawableCompat.setTint(drawable, Color.parseColor(chartLabel.labelColor))
        holder.itemView.setOnClickListener { onItemClicked(position) }
        holder.labelColor.background = drawable
    }

    override fun getItemCount() = chartLabelModels.size

    data class ChartLabelModel(
        val labelName: String,
        val labelColor: String
    )

}