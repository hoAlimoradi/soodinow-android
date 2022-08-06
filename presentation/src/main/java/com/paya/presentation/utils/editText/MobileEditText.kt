package com.paya.presentation.utils.editText

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import com.paya.presentation.utils.Utils
import kotlinx.android.synthetic.main.input_phone_number.view.*


class MobileEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.input_phone_number, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.MobileEditText)
        try {
            labelText.text = styles.getString(R.styleable.MobileEditText_labelText)
            if (styles.getString(R.styleable.MobileEditText_android_hint).isNullOrEmpty())
                phoneNumberEditText.hint = styles.getString(R.styleable.MobileEditText_android_hint)
            phoneNumberEditText.setHintTextColor(
                styles.getColor(
                    R.styleable.MobileEditText_hintTextColor,
                    ContextCompat.getColor(context, R.color.alto_light)
                )
            )
            val inputType: Int =
                styles.getInt(R.styleable.MobileEditText_android_inputType, EditorInfo.TYPE_NULL)
            if (inputType != EditorInfo.TYPE_NULL) {
                phoneNumberEditText.inputType = inputType
            }
            Utils.setVerificationImage(phoneNumberEditText,verification_img)

        } finally {
            styles.recycle()
        }

    }

    fun setError(error:String) {
        errorLayout?.let {
            it.setError(error)
        }
    }

    fun setText(text: String) {
        phoneNumberEditText?.apply {setText(text) }
    }

    fun getText(): String {
        return phoneNumberEditText?.text?.toString() ?: ""
    }

    fun setLabelText(label:String) {
        labelText?.apply {
            text = label
        }
    }

    fun setHint(hint:String) {
        phoneNumberEditText?.apply {
            this.hint = hint
        }
    }

}