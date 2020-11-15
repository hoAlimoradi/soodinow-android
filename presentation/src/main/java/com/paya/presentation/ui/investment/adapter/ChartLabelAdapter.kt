package com.paya.presentation.ui.investment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.ui.home.adapter.MarketAdapter

class ChartLabelAdapter(
	private val chartLabelModels: List<ChartLabelModel>
): RecyclerView.Adapter<ChartLabelAdapter.ChartLabelViewHolder>(){
	
	class ChartLabelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		val labelTxt: TextView = itemView.findViewById(R.id.label_txt)
		val labelColor: View = itemView.findViewById(R.id.label_color)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ChartLabelViewHolder {
		val inflate = LayoutInflater.from(parent.context)
		val view = inflate.inflate(R.layout.item_chart_label,parent,false)
		return ChartLabelViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: ChartLabelViewHolder,position: Int) {
		val chartLabel = chartLabelModels[position]
		
		holder.labelTxt.text = chartLabel.labelName
		
		val drawable = DrawableCompat.wrap(
			ContextCompat.getDrawable(
				holder.labelColor.context,
				R.drawable.round_small_radius_corner_layout
			)!!
		)
		DrawableCompat.setTint(drawable, Color.parseColor(chartLabel.labelColor))
		holder.labelColor.background = drawable
	}
	
	override fun getItemCount() = chartLabelModels.size
	
}