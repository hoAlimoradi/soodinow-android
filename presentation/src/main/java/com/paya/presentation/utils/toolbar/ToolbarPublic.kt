package com.paya.presentation.utils.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import kotlinx.android.synthetic.main.toolbar.view.*


class ToolbarPublic @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var backClick: () -> Unit = {}

    init {
        View.inflate(context, R.layout.toolbar, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.ToolbarPublic)
        try {
            titleToolbar.text = styles.getString(R.styleable.ToolbarPublic_toolbarTitle)
            titleToolbar.setTextColor(styles.getColor(R.styleable.ToolbarPublic_toolbarColor,ContextCompat.getColor(getContext(),R.color.purple_gray)))
        } finally {
            styles.recycle()
        }
        backButton.setOnClickListener {
            backClick.invoke()
        }
    }

}