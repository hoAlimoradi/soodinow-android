package com.paya.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.paya.presentation.R
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
    fun setSizeDialog(activity: Activity, window: Window) {
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
    fun setAutomaticSizeHeightDialog(activity: Activity, window: Window) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val dialogW = (width * 70) / 100
        window.setLayout(dialogW, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    @JvmStatic
    fun separatorAmount(amount: CharSequence?): String? {
        return separatorAmount(amount.toString().stringLocalizer().replace(",", "").toInt())
    }

    @JvmStatic
    fun separatorAmount(amount: String?): String? {
        return separatorAmount(amount.toString().stringLocalizer().replace(",", "").toInt())
    }

    @JvmStatic
    fun getAmount(amount: String?): Long? {
        if (amount.isNullOrEmpty())
            return 0
        return amount.toString().stringLocalizer().replace(",", "").toLong()
    }

    @JvmStatic
    fun separatorAmount(amount: Int): String? {
      return separatorAmount(amount.toLong())
    }
    @JvmStatic
    fun separatorAmount(amount: Long): String? {
        return try {
            val value = amount.toString().stringLocalizer()
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
        if(s.isNullOrEmpty())
            return 0f;
        return s.stringLocalizer().replace(",", "").toFloat()
    }


    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun convertToDate(pDate: PersianCalendar): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(pDate.time).stringLocalizer()
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun convertToDate(pDate: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(pDate.time).stringLocalizer()
    }

    @JvmStatic
    fun convertStringToDate(date: String): Date? {
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
    fun openPersianCalender(
        context: Context?,
        persianCalendar: PersianCalendar?,
        listener: Listener
    ) {
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
    fun roundNumber(value: Double?): Double? {
        value ?: return null
        return BigDecimal(value)
            .setScale(5, RoundingMode.HALF_UP).toDouble()
    }

    @JvmStatic
    fun roundNumber(value: Double): Double {
        return BigDecimal(value)
            .setScale(5, RoundingMode.HALF_UP).toDouble()
    }

    const val stepPrice = 1000


    @JvmStatic
    fun enableDisable(textView: TextView, value: Int?) {
        value ?: return
        if (value != 0)
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.gray))
        else
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
    }

    @JvmStatic
    fun setVerificationPinImage(editText: PinEntryEditText, imageView: ImageView) {
        setTintColor(imageView, editText.context, R.color.gray)

        editText.doAfterTextChanged {
            val text = it.toString()
            val colorId = if (text.length != 5) R.color.gray else R.color.green
            setTintColor(imageView, editText.context, colorId)
        }
    }

    @JvmStatic
    fun setVerificationImage(editText: EditText, imageView: ImageView) {
        setTintColor(imageView, editText.context, R.color.gray)

        editText.doAfterTextChanged {
            val text = it.toString()
            val colorId = if (text.length != 11) R.color.gray else R.color.green
            setTintColor(imageView, editText.context, colorId)
        }
    }

    fun setTintColor(
        imageView: ImageView,
        context: Context,
        colorId: Int
    ) {
        imageView.setColorFilter(
            ContextCompat.getColor(context, colorId),
            PorterDuff.Mode.SRC_IN
        )
    }

    @JvmStatic
    fun setSpannableRules(textView: TextView, value: String?, color: Int = Color.BLUE) {
        val spannable = SpannableString(textView.text)
        val start = value?.let { textView.text.indexOf(it) }
        val end = value?.let { it.length }
        if (start != null && end != null) {
            spannable.setSpan(
                ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    @JvmStatic
    fun calling(context: Context, phone: String) {
        val uri: Uri = Uri.parse("tel:" + phone.trim())
        val intent = Intent(Intent.ACTION_DIAL, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    @JvmStatic
    fun isPasswordInputType(inputType: Int): Boolean {
        val variation =
            inputType and (InputType.TYPE_MASK_CLASS or InputType.TYPE_MASK_VARIATION)
        return (variation
                == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) || (variation
                == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD) || (variation
                == InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)
    }
}