package com.paya.presentation.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class RtlGridLayoutManager(context: Context?, spanCount: Int) :
    GridLayoutManager(context, spanCount)
     {

    override fun isLayoutRTL(): Boolean = true

}