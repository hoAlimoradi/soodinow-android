package com.paya.presentation.ui.activitiesReport.enum

import com.paya.presentation.R


enum class StateInvestment(val title: String, val color: Int, val dot: Int) {
    Pending("در حال انتظار", R.color.governor_bay_blue,R.drawable.dot_goveronr_bay),
    Running("در حال انجام", R.color.orange,R.drawable.dot_orange),
    Completed("موفق", R.color.green,R.drawable.dot_green),
    Error("ناموفق", R.color.red,R.drawable.dot_red);

    companion object {
        @JvmStatic
        fun getStateWithString(state:String) : StateInvestment {
            values().forEach {
                if (state == it.name)
                    return it
            }
            return Pending
        }
    }
}