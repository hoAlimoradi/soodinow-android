package com.paya.presentation.ui.activitiesReport.enum

import com.paya.presentation.R


enum class StateInvestment(
    val title: String,
    val color: Int,
    val dot: Int,
    val bg: Int,
    val icon: Int
) {
    Pending(
        "در حال انتظار",
        R.color.governor_bay_blue,
        R.drawable.dot_goveronr_bay,
        R.drawable.goveronr_buy_gradiant_circle,
        R.drawable.ic_tick
    ),
    Running(
        "در حال انجام",
        R.color.orange,
        R.drawable.dot_orange,
        R.drawable.orange_gradiant_circle,
        R.drawable.ic_tick
    ),
    Completed(
        "موفق",
        R.color.green,
        R.drawable.dot_green,
        R.drawable.green_gradiant_circle,
        R.drawable.ic_tick
    ),
    Error(
        "ناموفق",
        R.color.red,
        R.drawable.dot_red,
        R.drawable.dialog_error_circle,
        R.drawable.ic_close_white
    );

    companion object {
        @JvmStatic
        fun getStateWithString(state: String): StateInvestment {
            values().forEach {
                if (state == it.name)
                    return it
            }
            return Pending
        }
    }
}