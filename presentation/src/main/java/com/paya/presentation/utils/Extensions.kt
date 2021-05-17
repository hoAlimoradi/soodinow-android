package com.paya.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Fragment.longToast(text: String) {
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun Fragment.shortToast(text: String) {
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}

fun Activity.longToast(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity.shortToast(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_SHORT
    ).show()
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun DialogFragment.setFullScreen() {
    dialog?.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
}

fun Date.getTimeHoursAndMinute(): String {
    val cal = Calendar.getInstance()
    cal.time = this
    val hours = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    return "$hours : $minute"
}

fun isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
        .matches()
}

fun Date.getDifferenceDate(date: String): String {

    val endDateValue = Date()
    val startDateValue = convertDate(date)
    startDateValue?.let {
        val diff: Long = endDateValue.time - it.time
        val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        val month = day / 60
        val year = month / 12
        return when {
            month in 1..11 -> {
                "$month ماه قبل "
            }
            year >= 1 -> {
                "$year سال قبل "
            }
            else -> {
                "$day روز قبل "
            }
        }
    }
    return ""
}

fun convertDate(date: String): Date? {
    val formats = arrayListOf<SimpleDateFormat>()
    formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()))
    formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()))
    formats.add(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
    formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()))
    for (format in formats) {
        try {
            return format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    return null
}

fun convertToPersianDate(date: String): PersianCalendar {
    convertDate(date)?.let {
        return PersianCalendar(it.time)
    }
    return PersianCalendar(Date().time)
}

fun getBeforeDay(timeStamp: Long, day: Int): PersianCalendar {
    var persianCalendar = PersianCalendar(timeStamp)
    persianCalendar.add(Calendar.DAY_OF_WEEK, day)
    return persianCalendar

}

fun getBeforeHour(timeStamp: Long, hour: Int): PersianCalendar {
    var persianCalendar = PersianCalendar(timeStamp)
    persianCalendar.add(Calendar.HOUR_OF_DAY, hour)
    return persianCalendar
}

fun getBeforeMonth(timeStamp: Long, month: Int): PersianCalendar {
    var persianCalendar = PersianCalendar(timeStamp)
    persianCalendar.add(Calendar.MONTH, month)
    return persianCalendar
}

fun PersianCalendar.getHourPersianDate(): Int {
    return get(Calendar.HOUR_OF_DAY)
}

fun getVersionName(context: Context): String {
    val manager = context.packageManager
    val info = manager.getPackageInfo(context.packageName, 0)
    return info.versionName
}

fun DialogFragment.openLink(link: String) {
    if (link.isNotEmpty())
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(link)))
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun convertDpToPixels(dp: Float, resources: Resources): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )
}

fun getCardHeight(context: Context,margin: Float): Float {
    val defaultWidth = convertDpToPixels(291f, context.resources)
    val defaultHeight = convertDpToPixels(150f, context.resources)
    val screenWidth = getScreenWidth().toFloat() - margin
    return (screenWidth * defaultHeight) / defaultWidth

}