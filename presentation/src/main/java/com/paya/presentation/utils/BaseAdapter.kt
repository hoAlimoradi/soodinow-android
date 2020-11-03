package com.paya.presentation.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<VH : RecyclerView.ViewHolder?,Item> : RecyclerView.Adapter<VH>() {
	
	var data: MutableList<Item> = mutableListOf()
	
	abstract fun onCreateHolder(parent: ViewGroup,viewType: Int): VH
	abstract fun onBindHolder(holder: VH,item: Item,position: Int)
	
	private var onClickListenerItem: OnClickListenerItem<Item>? = null
	
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): VH {
		return onCreateHolder(parent,viewType)
	}
	
	override fun onBindViewHolder(holder: VH,position: Int) {
		var item: Item = data[position]
		holder!!.itemView.setOnClickListener {
			if (onClickListenerItem != null) {
				onClickListenerItem!!.onClickListenerItem(this@BaseAdapter,item,position)
			}
		}
		onBindHolder(holder,item,position)
	}
	
	override fun getItemCount(): Int {
		return data.size
	}
	
	fun addLastItem(item: Item) {
		this.data.add(item)
		notifyItemChanged(this.data.size - 1)
	}
	
	fun addAllData(items: ArrayList<Item>) {
		this.data.addAll(items)
		notifyDataSetChanged()
	}
	
	fun addFakeItem(item : Item) {
		addLastItem(item)
		addLastItem(item)
		addLastItem(item)
		addLastItem(item)
		addLastItem(item)
	}
	
	fun addOnClickListenerItem(onClickListenerItem: OnClickListenerItem<Item>) {
		this.onClickListenerItem = onClickListenerItem
	}
	
	interface OnClickListenerItem<Item> {
		fun onClickListenerItem(adapter: BaseAdapter<*,*>,item: Item,position: Int)
	}
}