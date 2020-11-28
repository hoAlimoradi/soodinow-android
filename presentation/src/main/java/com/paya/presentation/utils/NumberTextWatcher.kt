package com.paya.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.ParseException


class NumberTextWatcher(
	private val et: EditText,
	pattern: String?,
	private val coroutineScope: CoroutineScope? = null,
	private val onTextChanged: (String) -> Unit
) : TextWatcher {
	private var searchFor = ""
	private val df: DecimalFormat = DecimalFormat(pattern)
	private var hasFractionalPart: Boolean
	override fun afterTextChanged(s: Editable?) {
		val searchText = s.toString().trim()
		if (searchText == searchFor)
			return
		
		searchFor = searchText
		
		coroutineScope?.launch {
			delay(2000)  //debounce timeOut
			if (searchText != searchFor)
				return@launch
			
			onTextChanged(searchText)
		}
		if (s == null || s.isEmpty())
			return
		et.removeTextChangedListener(this)
		val number = s.toString().filter {
			it.isDigit()
		}.toLongOrNull()
		et.setText(df.format(number).toString().replace(".", ""))
		et.setSelection(et.text.length)
		et.addTextChangedListener(this)
	}
	
	override fun beforeTextChanged(s: CharSequence,start: Int,count: Int,after: Int) {}
	override fun onTextChanged(s: CharSequence,start: Int,before: Int,count: Int) {}
	
	init {
		df.isDecimalSeparatorAlwaysShown = true
		hasFractionalPart = false
	}
}