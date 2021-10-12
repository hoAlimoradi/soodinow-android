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
) : FrameLayout(context, attrs, defStyleAttr) {

    /*
    init {
        View.inflate(context, R.layout.custom_radio_button, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton)
        try {
            labelText.text = styles.getString(R.styleable.InputEditText_labelText)
            inputContentEditText.hint = styles.getString(R.styleable.InputEditText_android_hint)
            inputContentEditText.setHintTextColor(
                styles.getColor(
                    R.styleable.InputEditText_hintTextColor,
                    ContextCompat.getColor(context, R.color.blue_light)
                )
            )
            val inputType: Int =
                styles.getInt(R.styleable.InputEditText_android_inputType, EditorInfo.TYPE_NULL)
            if (inputType != EditorInfo.TYPE_NULL) {
                inputContentEditText.inputType = inputType
            }

            showPasswordBtn.visibility =
                if (Utils.isPasswordInputType(inputType)) View.VISIBLE else View.GONE
            showPasswordBtn.setOnClickListener {
                if (isShowPassword) {
                    inputContentEditText.transformationMethod = null
                    showPasswordBtn.setImageResource(R.drawable.ic_eye)
                    isShowPassword = false
                } else {
                    inputContentEditText.transformationMethod =
                        PasswordTransformationMethod()
                    showPasswordBtn.setImageResource(R.drawable.ic_eye_disable)
                    isShowPassword = true
                }
                inputContentEditText.setSelection(inputContentEditText.text.toString().length)

            }

        } finally {
            styles.recycle()
        }

    }*/
    init {
        /*customRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.text_size_14sp)) //<dimen name="text_medium">14sp</dimen>
        customRadioButton.setTextColor(ContextCompat.getColorStateList(context, R.color.emperor))
        customRadioButton.typeface = getIranSans(context)
        customRadioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
        customRadioButton.gravity = Gravity.CENTER_VERTICAL
        customRadioButton.gravity = Gravity.RIGHT*/
    }

    fun setText(text: String) {
        customRadioButton?.apply { setText(text) }
    }

    fun getText(): String {
        return customRadioButton?.text?.toString() ?: ""
    }

}