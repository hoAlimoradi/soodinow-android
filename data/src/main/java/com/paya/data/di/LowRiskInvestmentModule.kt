package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.IsInRiskListRemoteRepoMapper
import com.paya.data.repository.LowRiskInvestmentRepositoryImpl
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LowRiskInvestmentModule {
	
	@Binds
	abstract fun bindIsInRiskRemoteRepoMapper(mapper: IsInRiskListRemoteRepoMapper): Mapper<
			IsInRiskListRemoteModel,
			IsInRiskListRepoModel>
	
	@Binds
	abstract fun bindRepo(repo: LowRiskInvestmentRepositoryImpl): LowRiskInvestmentRepository
	
}