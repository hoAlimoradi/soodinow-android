package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.CryptoPriceRemoteMapper
import com.paya.data.mapper.CurrencyPriceRemoteRepoMapper
import com.paya.data.repository.CurrencyPriceRepositoryImpl
import com.paya.domain.models.remote.CryptoPriceRemoteModel
import com.paya.domain.models.remote.CurrencyPriceRemoteModel
import com.paya.domain.models.repo.CurrencyPriceRepoModel
import com.paya.domain.repository.CurrencyPriceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CurrencyPriceModule {
	
	@Binds
	abstract fun bindCryptoMapper(mapper: CryptoPriceRemoteMapper): Mapper<
			CryptoPriceRemoteModel,
			CurrencyPriceRepoModel>
	
	@Binds
	abstract fun bindCurrencyMapper(mapper: CurrencyPriceRemoteRepoMapper): Mapper<
			CurrencyPriceRemoteModel,
			CurrencyPriceRepoModel>
	
	@Binds
	abstract fun bindRepository(repo: CurrencyPriceRepositoryImpl): CurrencyPriceRepository
	
}