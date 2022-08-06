/*
package com.paya.presentation.utils

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import kotlinx.android.synthetic.main.custom_radio_button.view.*
import kotlinx.android.synthetic.main.input_text.view.*

class CustomRadioButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) , View.OnClickListener{

    init {
        View.inflate(context, R.layout.custom_radio_button, this)
        customRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.text_size_14sp)) //<dimen name="text_medium">14sp</dimen>
        customRadioButton.setTextColor(ContextCompat.getColorStateList(context, R.color.emperor))
        customRadioButton.typeface = getIranSans(context)
        customRadioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
        customRadioButton.gravity = Gravity.CENTER_VERTICAL
        customRadioButton.gravity = Gravity.RIGHT
    }

    fun setText(text: String) {
        customRadioButton?.apply { setText(text) }
    }

    fun getText(): String {
        return customRadioButton?.text?.toString() ?: ""
    }

    override fun onClick(v: View?) {
        customRadioButton.isChecked = !(customRadioButton.isChecked)
    }

    fun isChecked(): Boolean {
        return customRadioButton.isChecked
    }

    fun setCheckValue(check: Boolean) {
          customRadioButton.isChecked = check
    }

}*/
