package com.paya.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class WrapContentViewPager @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : ViewPager(context,attrs) {
	
	private var mCurrentView: View? = null
	override fun onMeasure(widthMeasureSpec: Int,heightMeasureSpec: Int) {
		if (mCurrentView == null) {
			super.onMeasure(widthMeasureSpec,heightMeasureSpec)
			return
		}
		var height = 0
		mCurrentView!!.measure(
			widthMeasureSpec,
			MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED)
		)
		var h = mCurrentView!!.measuredHeight;
		if (h > height) height = h;
		
		super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
	}
	
	fun measureCurrentView(currentView: View) {
		mCurrentView = currentView;
		requestLayout();
	}
	
	fun measureFragment(view: View): Int {
		if (view == null)
			return 0;
		
		view.measure(0,0);
		return view.measuredHeight;
	}
}