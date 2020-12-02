package com.paya.domain.repository

import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.tools.Resource

interface CurrencyPriceRepository {
	suspend fun getCurrencyPrice(): Resource<List<CurrencyPriceRepoModel>>
}