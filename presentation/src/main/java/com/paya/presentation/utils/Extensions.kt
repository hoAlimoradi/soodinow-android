package com.paya.presentation.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

fun Fragment.longToast(text: String) {
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun Fragment.shortToast(text: String) {
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}

fun Activity.longToast(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity.shortToast(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_SHORT
    ).show()
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}