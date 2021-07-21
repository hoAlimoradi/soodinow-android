package com.paya.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import java.io.UnsupportedEncodingException
import java.math.RoundingMode
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

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

fun Fragment.openUrl(url: String) {
    if (url.isEmpty() || !URLUtil.isValidUrl(url))
        return
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Activity.openUrl(url: String) {
    if (url.isEmpty() || !URLUtil.isValidUrl(url))
        return
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun DialogFragment.openUrl(url: String) {
    if (url.isEmpty() || !URLUtil.isValidUrl(url))
        return
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun String.isSecretPassword(): Boolean {
    val passwordPattern = "^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z]).{8,21}\$"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun Int.remainingTime(): String {
    return when {
        this == 120 -> {
            "02:00"
        }
        this >= 60 -> {
            "01:${this - 60}"
        }
        else -> "00:${this}"
    }
}

fun String.isNationalCode(): Boolean {
    if (this.length != 10) {
        return false
    }
    if (!this.isDigitsOnly()) {
        return false
    }
    var sum = 0
    var controlNumber = this[9].toString().toInt()
    for (index in 10 downTo 2) {
        sum += this[10 - index].toString().toInt() * index
    }
    val remaining = (sum % 11)
    if (remaining < 2 && remaining == controlNumber) {
        return true
    }
    if (remaining >= 2 && (11 - remaining) == controlNumber) {
        return true
    }
    return false
}

fun String.md5(): String? {
    if (this.isEmpty())
        return ""
    try {
        val md = MessageDigest.getInstance("MD5")
        val array = md.digest(this.toByteArray())
        val sb = StringBuffer()
        for (i in array.indices) {
            sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
        }
        return sb.toString()
    } catch (e: java.security.NoSuchAlgorithmException) {
    } catch (ex: UnsupportedEncodingException) {
    }
    return null
}

fun String.isMobile(): Boolean {
    return length == 11 && startsWith("09")
}

fun String.startWithCountryCodeMobile(): String {
    if (startsWith("0", false)) {
        val newMobile = substring(1, length)
        return "+98$newMobile"
    }
    return this
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

fun RecyclerView.removeAllDecoration() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}

fun getIranSans(context: Context?): Typeface? {
    context?.let {
        return Typeface.createFromAsset(it.assets, it.getString(R.string.font_farsi_regular))
    }
    return null
}

fun isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
        .matches()
}

fun Date.getDifferenceDate(date: String): String {

    val endDateValue = Date()
    val startDateValue = convertDateWithoutHour(date)
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
                if (day == 0L) "امروز" else if (day == 1L) "دیروز" else "$day روز قبل "
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

fun convertDateWithoutHour(date: String): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    try {
        return format.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
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

fun roundOffDecimal(number: Float): Float? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toFloat()
}


fun TextView.setArrayStringText(
    stringArray: Array<String>,
    colorArrayText: Int
) {
    text = BulletTextUtil.makeBulletListFromStringArrayResource(
        context.resources.getDimension(R.dimen.margin_s).toInt(),
        context,
        colorArrayText,
        stringArray
    )
}

fun View.setFocusTarget(target: EditText?) {
    target ?: return
    setOnClickListener {
        target.requestFocus()
        val imm: InputMethodManager? =
            target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
    }
}



