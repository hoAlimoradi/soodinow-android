package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.AddRiskOrderRemoteRepoMapper
import com.paya.data.mapper.AddRiskOrderRepoRemoteMapper
import com.paya.data.mapper.ExitAccountRemoteRepoMapper
import com.paya.data.mapper.IsInRiskListRemoteRepoMapper
import com.paya.data.repository.LowRiskInvestmentRepositoryImpl
import com.paya.domain.models.remote.AddRiskOrderRemoteBodyModel
import com.paya.domain.models.remote.AddRiskOrderRemoteModel
import com.paya.domain.models.remote.ExitAccountRemoteModel
import com.paya.domain.models.remote.IsInRiskListRemoteModel
import com.paya.domain.models.repo.AddRiskOrderRepoBodyModel
import com.paya.domain.models.repo.AddRiskOrderRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.IsInRiskListRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.order.AddRiskOrderUseCase
import com.paya.domain.usecase.order.ExitAccountUseCase
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
	abstract fun bindExitAccountRemoteRepoMapper(mapper: ExitAccountRemoteRepoMapper): Mapper<
			ExitAccountRemoteModel,
			ExitAccountRepoModel>
	
	@Binds
	abstract fun bindAddRiskOrderRemoteRepoMapper(mapper: AddRiskOrderRemoteRepoMapper): Mapper<
			AddRiskOrderRemoteModel,
			AddRiskOrderRepoModel>
	
	@Binds
	abstract fun bindAddRiskOrderRepoRemoteMapper(mapper: AddRiskOrderRepoRemoteMapper): Mapper<
			AddRiskOrderRepoBodyModel,
			AddRiskOrderRemoteBodyModel>
	
	@Binds
	abstract fun bindRepo(repo: LowRiskInvestmentRepositoryImpl): LowRiskInvestmentRepository
	
	@Binds
	abstract fun bindExitAccountUseCase(useCase: ExitAccountUseCase): UseCase<Unit,ExitAccountRepoModel>
	
	@Binds
	abstract fun bindAddRiskOrderUseCase(useCase: AddRiskOrderUseCase) : UseCase<AddRiskOrderRepoBodyModel,AddRiskOrderRepoModel>
	
}