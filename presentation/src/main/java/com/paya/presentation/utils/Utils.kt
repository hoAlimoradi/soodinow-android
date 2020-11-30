package com.paya.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
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
		val persianDate = PersianDate()
		val format = PersianDateFormat("YYYY-mm-dd")
		format.format(persianDate)
		persianDate.shYear = pDate.persianYear
		persianDate.shMonth = pDate.persianMonth
		persianDate.shDay = pDate.persianDay
		val sdf = SimpleDateFormat("yyyy-MM-dd")
		return sdf.format(persianDate.toDate())
	}
	
	@JvmStatic
	fun convertStringPersianDate(date: String): PersianDate? {
		val persianDate = PersianDate()
		val format = PersianDateFormat("Y-m-d'T'HH:m:s.SSS'Z'")
		format.format(persianDate)
		val gDate = convertStringToDate(date)
		gDate?.let {
			val calendar: Calendar = GregorianCalendar()
			
			calendar.time = it
			persianDate.grgYear = calendar.get(Calendar.YEAR)
			persianDate.grgMonth = calendar.get(Calendar.MONTH) + 1
			persianDate.grgDay = calendar.get(Calendar.DAY_OF_MONTH)
			persianDate.hour = calendar.get(Calendar.HOUR_OF_DAY)
			persianDate.minute = calendar.get(Calendar.MINUTE)
			persianDate.second = calendar.get(Calendar.SECOND)
			return persianDate
		}
		
		return null
	}
	
	
	@JvmStatic
	fun convertStringToDate(date: String): Date? {
		val formats = arrayListOf<SimpleDateFormat>()
		formats.apply {
			add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault()))
			add(SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()))
			add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.getDefault()))
		}
		formats.forEach {
			try {
				return it.parse(date)
			} catch (e: ParseException) {
				e.printStackTrace()
			}
		}
		return null
	}

	@JvmStatic
	fun roundFloat(value: Float): Float{
		return BigDecimal(value.toDouble())
			.setScale(2, RoundingMode.HALF_UP).toFloat()
	}
	
}