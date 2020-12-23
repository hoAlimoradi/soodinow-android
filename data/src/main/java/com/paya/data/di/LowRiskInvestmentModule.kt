package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.*
import com.paya.data.repository.LowRiskInvestmentRepositoryImpl
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.order.*
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
            AddRiskOrderItem,
            AddRiskOrderRepoItem>

    @Binds
    abstract fun bindAddRiskOrderRepoRemoteMapper(mapper: AddRiskOrderRepoRemoteMapper): Mapper<
            AddRiskOrderRepoBodyModel,
            AddRiskOrderRemoteBodyModel>
    @Binds
    abstract fun bindPullPriceRemoteRepoMapper(mapper: PullPriceRemoteRepoMapper): Mapper<
            Unit,
            Unit>

    @Binds
    abstract fun bindSellPriceMapperRemoteRepoMapper(mapper: GetSellPriceMapperRemoteRepoMapper): Mapper<@JvmSuppressWildcards  List<List<Long>>,@JvmSuppressWildcards  List<Long>>

    @Binds
    abstract fun bindBoxTypesRemoteRepoMapper(mapper: BoxTypesRemoteRepoMapper): Mapper<BoxTypeRemoteModel, BoxTypeRepoModel>

    @Binds
    abstract fun bindRepo(repo: LowRiskInvestmentRepositoryImpl): LowRiskInvestmentRepository

    @Binds
    abstract fun bindExitAccountUseCase(useCase: ExitAccountUseCase): UseCase<Unit, ExitAccountRepoModel>

    @Binds
    abstract fun bindSellPriceUseCase(useCase: SellPriceUseCase): UseCase<String,@JvmSuppressWildcards  List<Long>>

    @Binds
    abstract fun bindAddRiskOrderUseCase(useCase: AddRiskOrderUseCase): UseCase<AddRiskOrderRepoBodyModel, List<AddRiskOrderRepoItem>>

    @Binds
    abstract fun bindABoxTypesUseCase(useCase: BoxTypesUseCase): UseCase<Unit, BoxTypeRepoModel>
    @Binds
    abstract fun bindPullPriceUseCase(useCase: PullPriceUseCase): UseCase<PullPriceBodyRepoModel, Unit>

}