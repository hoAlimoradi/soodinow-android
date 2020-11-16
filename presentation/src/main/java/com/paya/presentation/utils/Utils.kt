package com.paya.presentation.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.Window

object Utils {
	@JvmStatic
	fun setSizeDialog(activity: Activity,window : Window) {
		val displayMetrics = DisplayMetrics()
		activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		val height = displayMetrics.heightPixels
		val dialogW = (width * 70) / 100
		val dialogH = (height * 60) / 100
		window.setLayout(
			dialogW,
			dialogH
		)
		window.setGravity(Gravity.CENTER)
		window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
	}
	
	@JvmStatic
	fun separatorAmount(amount: CharSequence?): String? {
		return separatorAmount(amount.toString().replace(",","").toInt())
	}
	@JvmStatic
	fun separatorAmount(amount: Int): String? {
		return try {
			val value = amount.toString()
			val reverseValue = StringBuilder(value).reverse().toString()
			val finalValue = StringBuilder()
			for (i in 1..reverseValue.length) {
				val `val` = reverseValue[i - 1]
				finalValue.append(`val`)
				if (i % 3 == 0 && i != reverseValue.length && i > 0) {
					finalValue.append(",")
				}
			}
			StringBuilder(finalValue).reverse().toString()
		} catch (e: Exception) {
			""
		}
	}
	
	
	@JvmStatic
	fun convertToFloatAmount(s: String): Float {
		return s.replace(",","").toFloat()
	}
}