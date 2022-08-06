package com.paya.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

const val overlap = -20

class OverlapDecoration : ItemDecoration() {
	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		super.getItemOffsets(outRect,view,parent,state)
		if(parent.getChildAdapterPosition(view)==0)
			return
		outRect.set(overlap,0,0,0)
		
	}
	
	
	
}