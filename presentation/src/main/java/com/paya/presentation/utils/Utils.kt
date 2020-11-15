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
		val dialogW = (width*70)/100
		val dialogH = (height*60)/100
		window.setLayout(
			dialogW,
			dialogH
		)
		window.setGravity(Gravity.CENTER)
		window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
	}
}