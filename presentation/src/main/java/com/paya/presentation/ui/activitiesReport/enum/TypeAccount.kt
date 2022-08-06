package com.paya.presentation.ui.activitiesReport.enum

enum class TypeAccount(val title: String) {
    Fixed ("حساب در آمد ثابت");
    companion object {
        @JvmStatic
        fun getTypeWithString(type:String) : TypeAccount{
            values().forEach {
                if(type == it.title)
                    return it
            }
            return Fixed
        }
    }
}