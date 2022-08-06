package com.paya.presentation.utils.customRadio

import android.view.View
import android.widget.Checkable

interface CustomRadioCheckable: Checkable {
    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)
    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)

    interface OnCheckedChangeListener {
        fun onCheckedChanged(radioGroup: View, isChecked: Boolean)
    }
}