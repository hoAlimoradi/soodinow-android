package com.paya.domain.models.repo

data class CashManagerRepModel(var pricingCash: List<PricingCash>)


data class PricingCash(val min:Long,val max:Long)