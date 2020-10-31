package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.*
import com.paya.data.repository.MarketRepositoryImpl
import com.paya.domain.models.remote.MarketRemoteModel
import com.paya.domain.models.repo.*
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.market.MarketSmallListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MarketModule {
	
	@Binds
	abstract fun bindGetMarketSmallListMapperRemoteRepo(mapper: MarketRemoteRepoMapper): Mapper<
			MarketRemoteModel,
			MarketRepoModel
			>
	
	@Binds
	abstract fun accountRepo(dev: MarketRepositoryImpl): MarketRepository
	
	
	
	@Binds
	abstract fun bindMarketSmallListUseCase(useCase: MarketSmallListUseCase): UseCase<Unit,MarketRepoModel>
	
}