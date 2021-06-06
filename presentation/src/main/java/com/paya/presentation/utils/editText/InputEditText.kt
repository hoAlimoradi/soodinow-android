package com.paya.presentation.utils.editText

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import kotlinx.android.synthetic.main.input_text.view.*


class InputEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.input_text, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.InputEditText)
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
        inputContentEditText?.apply { setText(text) }
    }

    fun getText(): String {
        return inputContentEditText?.text?.toString() ?: ""
    }

    fun setLabelText(label:String) {
        labelText?.apply {
            text = label
        }
    }

    fun setHint(hint:String) {
        inputContentEditText?.apply {
            this.hint = hint
        }
    }
}