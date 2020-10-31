package com.paya.domain.repository

import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface MarketRepository {
	suspend fun getSmallListMarket(): Resource<MarketRepoModel>
	
}