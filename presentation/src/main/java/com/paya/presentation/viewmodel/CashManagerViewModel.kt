package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject

import com.paya.domain.models.repo.PricingCash

import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.Utils


class CashManagerViewModel @ViewModelInject constructor(

) : BaseViewModel() {
    val price = ObservableField<String>()
    val priceCashList = ObservableField<MutableList<Int>>()
    val minSeek= ObservableField<Int>()
    val maxSeek= ObservableField<Int>()

    init {
        var list = mutableListOf<PricingCash>()
        list.add(PricingCash(2000000, 2000000))
        list.add(PricingCash(6000000, 6000000))
        list.add(PricingCash(8000000, 8000000))
        list.add(PricingCash(13000000, 20000000))
        list.add(PricingCash(25000000, 28000000))
        var seekList = mutableListOf<Int>()
        list.forEach() {
            seekList.add(it.min)
            if (it.min!=it.max) {
               seekList.add(it.max)

            }
        }
        priceCashList.set(seekList)
        minSeek.set(2000000)
        maxSeek.set(28000000)
        price.set(Utils.separatorAmount(2000000))
    }


}