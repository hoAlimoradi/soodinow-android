package com.paya.presentation.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class RtlLinearLayoutManager(context: Context?, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    override fun isLayoutRTL(): Boolean = true

}