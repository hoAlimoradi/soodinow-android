package com.paya.presentation.ui.activitiesReport.enum

import com.paya.presentation.R

enum class TypeInvestment(val icon: Int) {
    Open(R.drawable.ic_arrow_dposit),
    Increase(R.drawable.ic_arrow_dposit),
    Reduction(R.drawable.ic_arrow_withdrawal);

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