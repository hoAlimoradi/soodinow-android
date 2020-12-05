package com.paya.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

const val overlap = -10

class OverlapDecoration : ItemDecoration() {
	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		val rec = Rect()
		rec.set(0,0,0,overlap)
		super.getItemOffsets(rec,view,parent,state)
		
	}
	
	
	
}