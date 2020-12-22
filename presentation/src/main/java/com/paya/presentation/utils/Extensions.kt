package com.paya.presentation.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.longToast(text: String){
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun Fragment.shortToast(text: String){
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}