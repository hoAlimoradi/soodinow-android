package com.paya.presentation.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.paya.presentation.R

object BindingAdapters {
	
	@JvmStatic
	@BindingAdapter("enableDisable")
	fun setTime(textView: TextView,value: Int?) {
		value ?: return
		if (value != 0)
			textView.setTextColor(ContextCompat.getColor(textView.context,R.color.gray))
		else
			textView.setTextColor(ContextCompat.getColor(textView.context,R.color.green))
	}

	
}