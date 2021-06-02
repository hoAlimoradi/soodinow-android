package com.paya.domain.repository

import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.tools.Resource
import retrofit2.http.Header

interface CurrencyPriceRepository {
	suspend fun getCurrencyPrice(): Resource<List<CurrencyPriceRepoModel>>
}