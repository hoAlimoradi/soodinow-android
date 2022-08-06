package com.paya.presentation.ui.activitiesReport.enum

import com.paya.presentation.R

enum class TypeInvestment(val icon: Int,val title:String) {
    Open(R.drawable.ic_arrow_dposit,"خرید"),
    Increase(R.drawable.ic_arrow_dposit,"خرید"),
    Reduction(R.drawable.ic_arrow_withdrawal,"برداشت");

    companion object {
        @JvmStatic
        fun getTypeWithString(type:String) : TypeInvestment{
            values().forEach {
                if(it.name == type)
                    return it
            }
            return Open
        }
    }
}