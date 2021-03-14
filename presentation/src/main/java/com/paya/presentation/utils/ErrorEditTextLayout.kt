package com.paya.presentation.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.paya.presentation.R

open class ErrorEditTextLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var textView: TextView? = null


    init {
        orientation = VERTICAL
        textView = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.topMargin = resources.getDimension(R.dimen.margin_s).toInt()
        textView!!.layoutParams = params
        textView!!.setTextColor(Color.RED)
        textView!!.textSize = resources.getDimension(R.dimen.text_size_error)
    }

    open fun setError(error:String) {
        when {
            error.isNullOrEmpty() -> {
                removeView(textView);
            }
            textView!!.parent==null -> {
                addView(textView)
                textView!!.text = error
            }
            else -> {
                textView!!.text = error
            }
        }
    }
}