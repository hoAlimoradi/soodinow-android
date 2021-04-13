package com.paya.presentation.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.paya.presentation.R


class ClearableEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatEditText(context, attrs, defStyleAttr), TextWatcher {
    private var mClearIconDrawable: Drawable? = null
    private var mClearEmptyIconDrawable: Drawable? = null
    private var mIsClearIconShown = false
    private var mClearIconDrawWhenFocused = true
    private var textClearedListener: OnTextClearedListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.editTextStyle
    )

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Load attributes

        val a: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText, defStyle, 0)
        mClearEmptyIconDrawable = ContextCompat.getDrawable(context, DEFAULT_CLEAR_EMPTY_ICON_RES_ID)
        mClearIconDrawable = if (a.hasValue(R.styleable.ClearableEditText_clearIconDrawable)) {
            a.getDrawable(R.styleable.ClearableEditText_clearIconDrawable)
        } else {
            ContextCompat.getDrawable(
                context,
                DEFAULT_CLEAR_ICON_RES_ID
            )
        }
        if (mClearIconDrawable != null) {
            mClearIconDrawable!!.callback = this
        }
        compoundDrawablePadding = resources.getDimension(R.dimen.margin_m).toInt()
        mClearIconDrawWhenFocused =
            a.getBoolean(R.styleable.ClearableEditText_clearIconDrawWhenFocused, true)
        showClearIcon(!text.isNullOrEmpty())
        a.recycle()
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        // no operation
    }

    override fun afterTextChanged(s: Editable) {}
    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return if (mIsClearIconShown) ClearIconSavedState(
            superState,
            true
        ) else superState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ClearIconSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        mIsClearIconShown = state.isClearIconShown
        showClearIcon(mIsClearIconShown)
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (!mClearIconDrawWhenFocused || hasFocus()) {
            showClearIcon(!TextUtils.isEmpty(s))
        }
    }

     override fun onFocusChanged(
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        showClearIcon((!mClearIconDrawWhenFocused || focused) && !TextUtils.isEmpty(text))
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isClearIconTouched(event)) {
            text = null
            event.action = MotionEvent.ACTION_CANCEL
            showClearIcon(false)
            textClearedListener?.onTextCleared()
            return false
        }
        return super.onTouchEvent(event)
    }

    private fun isClearIconTouched(event: MotionEvent): Boolean {
        if (!mIsClearIconShown) {
            return false
        }
        val touchPointX = event.x.toInt()
        val widthOfView = width
        val compoundPadding =
            compoundPaddingStart
        return touchPointX <= compoundPadding
    }

    private fun showClearIcon(show: Boolean) {
        val drawables =
            compoundDrawablesRelative
        if (show) {
            // show icon on the right
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                mClearIconDrawable, drawables[1], drawables[2], drawables[3]
            )
        } else {
            // remove icon
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                mClearEmptyIconDrawable,
                drawables[1],
                drawables[2],
                drawables[3]
            )
        }
        mIsClearIconShown = show
    }

    private class ClearIconSavedState : BaseSavedState {
        val isClearIconShown: Boolean

        private constructor(source: Parcel) : super(source) {
            isClearIconShown = source.readByte().toInt() != 0
        }

        internal constructor(superState: Parcelable?, isClearIconShown: Boolean) : super(
            superState
        ) {
            this.isClearIconShown = isClearIconShown
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeByte((if (isClearIconShown) 1 else 0).toByte())
        }

        companion object {
            val CREATOR: Parcelable.Creator<ClearIconSavedState?> =
                object : Parcelable.Creator<ClearIconSavedState?> {
                    override fun createFromParcel(source: Parcel): ClearIconSavedState? {
                        return ClearIconSavedState(source)
                    }

                    override fun newArray(size: Int): Array<ClearIconSavedState?> {
                        return arrayOfNulls(size)
                    }
                }
        }
    }

    fun setOnTextClearedListener(textClearedListener: OnTextClearedListener?) {
        this.textClearedListener = textClearedListener
    }

    companion object {
        @DrawableRes
        private val DEFAULT_CLEAR_ICON_RES_ID: Int = R.drawable.ic_clear
        @DrawableRes
        private val DEFAULT_CLEAR_EMPTY_ICON_RES_ID: Int = R.drawable.ic_clear_empty
    }

    init {
        init(context, attrs, defStyleAttr)
    }

    interface OnTextClearedListener {
        fun onTextCleared()
    }
}