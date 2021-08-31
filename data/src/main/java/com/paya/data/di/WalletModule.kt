package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.*
import com.paya.data.repository.WalletRepositoryImpl
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.WalletRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.wallet.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class WalletModule {

    @Binds
    abstract fun bindWalletBuyRemoteRepoMapper(mapper: WalletBuyRemoteRepoMapper): Mapper<
            String,
            WalletBuyRepoModel
            >

    @Binds
    abstract fun bindWalletChargeRemoteRepoMapper(mapper: WalletChargeRemoteRepoMapper): Mapper<
            WalletChargeRemoteModel,
            WalletChargeRepoModel
            >

    @Binds
    abstract fun bindWalletPreWithdrawRemoteRepoMapper(mapper: WalletPreWithdrawRemoteRepoMapper): Mapper<
            WalletPreWithdrawRemoteModel,
            WalletPreWithdrawRepoModel
            >

    @Binds
    abstract fun bindWalletWithdrawRequestRemoteRepoMapper(mapper: WalletWithdrawRequestRemoteRepoMapper): Mapper<
            WalletWithdrawRequestRemoteModel,
            WalletWithdrawRequestRepoModel
            >

    @Binds
    abstract fun bindWalletPortfolioRemoteRepoMapper(mapper: WalletPortfolioRemoteRepoMapper): Mapper<
            WalletPortfolioRemoteModel,
            WalletPortfolioRepoModel
            >

    @Binds
    abstract fun bindWalletValueRemoteRepoMapper(mapper: WalletValueRemoteRepoMapper): Mapper<
            WalletValueRemoteModel,
            WalletValueRepoModel
            >

    @Binds
    abstract fun walletRepo(dev: WalletRepositoryImpl): WalletRepository

    @Binds
    abstract fun bindWalletChargeUseCase(useCase: WalletChargeUseCase): UseCase<WalletChargeRepoBodyModel, WalletChargeRepoModel>

    @Binds
    abstract fun bindWalletUseCase(useCase: WalletBuyUseCase): UseCase<WalletBuyRepoBodyModel, WalletBuyRepoModel>

    @Binds
    abstract fun bindWalletPerWithdrawUseCase(useCase: WalletPerWithdrawUseCase): UseCase<Int, WalletPreWithdrawRepoModel>

    @Binds
    abstract fun bindWalletWithdrawRequestUseCase(useCase: WalletWithdrawRequestUseCase): UseCase<Int, WalletWithdrawRequestRepoModel>

    @Binds
    abstract fun bindWalletPortfolioUseCase(useCase: WalletPortfolioUseCase): UseCase<Unit, WalletPortfolioRepoModel>

    @Binds
    abstract fun bindWalletValueUseCase(useCase: WalletValueUseCase): UseCase<Unit, WalletValueRepoModel>


}