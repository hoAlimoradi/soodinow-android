package com.paya.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
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
	
	@JvmStatic
	@BindingAdapter("visibleGone")
	fun showHide(view: View,show: Boolean) {
		view.visibility = if (show) View.VISIBLE else View.GONE
	}
	
	@JvmStatic
	@BindingAdapter("focusTarget")
	fun setFocusTarget(view: View,viewId: Int?) {
		viewId ?: return
		view.setOnClickListener {
			val target = view.findViewById<EditText>(viewId)
			target.requestFocus()
			val imm: InputMethodManager? =
				target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
			imm?.showSoftInput(target,InputMethodManager.SHOW_IMPLICIT)
		}
	}
	
	@JvmStatic
	@BindingAdapter("verificationImage")
	fun setVerificationImage(editText: EditText,viewId: Int?) {
		viewId ?: return
		
		val parent = editText.parent as View
		val imageView = parent.findViewById<ImageView>(viewId)
		
		editText.doAfterTextChanged {
			val text = it.toString()
			if (text.length != 9) {
				imageView.setImageResource(R.drawable.ic_verification)
			} else {
				imageView.setImageResource(R.drawable.ic_verification_green)
			}
		}
	}
	
}