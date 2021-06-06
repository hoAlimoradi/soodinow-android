package com.paya.presentation.utils.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.paya.presentation.R
import kotlinx.android.synthetic.main.toolbar_dialog.view.*


class ToolbarDialog @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var backClick: () -> Unit? = {}

    init {
        View.inflate(context, R.layout.toolbar_dialog, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.ToolbarDialog)
        try {
            titleDialog.text = styles.getString(R.styleable.ToolbarDialog_toolbarDialogTitle)
        } finally {
            styles.recycle()
        }
        closeBtn.setOnClickListener {
            backClick.invoke()
        }
    }

    fun setTitle(title:String) {
        titleDialog?.apply {
            text = title
        }
    }

}