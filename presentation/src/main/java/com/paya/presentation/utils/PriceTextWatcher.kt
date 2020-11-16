package com.paya.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import java.lang.ref.WeakReference

class PriceTextWatcher(editText: AppCompatEditText,var onChange: OnChange) : TextWatcher {
	private var weakReferenceEditText: WeakReference<AppCompatEditText>? = null
	private var change = false
	
	init {
		weakReferenceEditText = WeakReference<AppCompatEditText>(editText)
	}
	
	override fun beforeTextChanged(p0: CharSequence?,p1: Int,p2: Int,p3: Int) {
	
	}
	
	override fun onTextChanged(p0: CharSequence?,p1: Int,p2: Int,p3: Int) {
		onChange.change(p0)
		if (change) {
			change = false
			return
		}
		change = true
		val editText = weakReferenceEditText!!.get()
		editText!!.setText(Utils.separatorAmount(p0))
	}
	
	override fun afterTextChanged(p0: Editable?) {
		onChange.afterChange(p0)
	}
	
	interface OnChange {
		fun change(s: CharSequence?)
		fun afterChange(s: Editable?)
	}
}