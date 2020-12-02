package com.paya.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
	@JvmStatic
	fun setSizeDialog(activity: Activity,window: Window) {
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
		setTransparentBackgroundDialog(window)
	}
	
	@JvmStatic
	fun setTransparentBackgroundDialog(window: Window) {
		window.setGravity(Gravity.CENTER)
		window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
	}
	
	@JvmStatic
	fun setAutomaticSizeHeightDialog(activity: Activity,window: Window) {
		val displayMetrics = DisplayMetrics()
		activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		val dialogW = (width * 70) / 100
		window.setLayout(dialogW,ViewGroup.LayoutParams.WRAP_CONTENT);
		
		
	}
	
	@JvmStatic
	fun separatorAmount(amount: CharSequence?): String? {
		return separatorAmount(amount.toString().replace(",","").toInt())
	}
	
	@JvmStatic
	fun separatorAmount(amount: String?): String? {
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
	
	@SuppressLint("SimpleDateFormat")
	@JvmStatic
	fun convertToDate(pDate: PersianCalendar): String {
		val sdf = SimpleDateFormat("yyyy-MM-dd")
		return sdf.format(pDate.time)
	}
	
	
	@JvmStatic
	fun convertStringToDate(date: String): Date? {
		val formats = arrayListOf<SimpleDateFormat>()
		formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault()))
		formats.add(SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()))
		formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.getDefault()))
		for (format in formats) {
			try {
				return format.parse(date)
			} catch (e: ParseException) {
				e.printStackTrace()
			}
		}
		return null
	}
	
	@JvmStatic
	fun convertStringToPersianCalender(date: String): PersianCalendar? {
		val gDate = convertStringToDate(date)
		val persianCalendar = PersianCalendar()
		gDate?.let {
			val calendar: Calendar = GregorianCalendar()
			
			calendar.time = it
			persianCalendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND)
			)
			return persianCalendar
		}
		
		return null
	}
	
	@JvmStatic
	fun openPersianCalender(context: Context?,persianCalendar: PersianCalendar?,listener: Listener) {
		if (context == null)
			return
		PersianDatePickerDialog(context)
			.setPositiveButtonString("باشه")
			.setNegativeButton("بیخیال")
			.setTodayButton("امروز")
			.setTodayButtonVisible(true)
			.setInitDate(persianCalendar ?: PersianCalendar())
			.setMaxYear(PersianDatePickerDialog.THIS_YEAR)
			.setMinYear(1300)
			.setActionTextColor(Color.GRAY)
			.setListener(listener)
			
			.show()
	}

	@JvmStatic
	fun roundNumber(value: Double): Double{
		return BigDecimal(value)
			.setScale(5, RoundingMode.HALF_UP).toDouble()
	}
	
}