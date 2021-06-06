package com.paya.presentation.utils.editText

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import kotlinx.android.synthetic.main.input_text_without_background.view.*


class LabelEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.input_text_without_background, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.LabelEditText)
        try {
            labelText.text = styles.getString(R.styleable.LabelEditText_labelText)
            inputContentEditText.hint = styles.getString(R.styleable.LabelEditText_android_hint)
            inputContentEditText.setHintTextColor(
                styles.getColor(
                    R.styleable.LabelEditText_hintTextColor,
                    ContextCompat.getColor(context, R.color.blue_light)
                )
            )
            val inputType: Int =
                styles.getInt(R.styleable.LabelEditText_android_inputType, EditorInfo.TYPE_NULL)
            if (inputType != EditorInfo.TYPE_NULL) {
                inputContentEditText.inputType = inputType
            }
            inputContentEditText.isFocusable = styles.getInt(R.styleable.LabelEditText_visiblelabel, -1) != 0
            inputContentEditText.isFocusableInTouchMode = styles.getInt(R.styleable.LabelEditText_visiblelabel, -1) != 0
            if(styles.getInt(R.styleable.LabelEditText_visiblelabel, -1) == 0) {
                inputContentEditText.setOnTouchListener(null)
                inputContentEditText.setOnClickListener {
                    this.performClick()
                }
            }

        } finally {
            styles.recycle()
        }

    }

    fun setError(error:String) {
        layout?.let {
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