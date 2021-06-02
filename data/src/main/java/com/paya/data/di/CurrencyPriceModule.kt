package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.CommonMarktDataRemoteMapper
import com.paya.data.repository.CurrencyPriceRepositoryImpl
import com.paya.domain.models.remote.CommonMarktDataPriceRemoteModel
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
	abstract fun bindCryptoMapper(mapper: CommonMarktDataRemoteMapper): Mapper<
			@JvmSuppressWildcards List<CommonMarktDataPriceRemoteModel>,
			@JvmSuppressWildcards List<CurrencyPriceRepoModel>>
	
	@Binds
	abstract fun bindRepository(repo: CurrencyPriceRepositoryImpl): CurrencyPriceRepository
	
}