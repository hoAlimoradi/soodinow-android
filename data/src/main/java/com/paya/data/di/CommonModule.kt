package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.CheckVersionRemoteRepoMapper
import com.paya.data.mapper.CityRemoteRepoMapper
import com.paya.data.mapper.ConfigRemoteRepoMapper
import com.paya.data.repository.CommonRepositoryImpl
import com.paya.domain.models.remote.CheckVersionRemoteModel
import com.paya.domain.models.remote.ConfigRemoteModel
import com.paya.domain.models.remote.ProvinceRemoteModel
import com.paya.domain.models.repo.CheckVersionRepoModel
import com.paya.domain.models.repo.ConfigRepoModel
import com.paya.domain.models.repo.GetAppLinkRepoModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.common.CheckVersionUseCase
import com.paya.domain.usecase.common.CityUseCase
import com.paya.domain.usecase.common.GetAppLinkUseCase
import com.paya.domain.usecase.common.GetConfigUseCase
import com.paya.domain.usecase.farabi.FarabiAuthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class CommonModule {


    @Binds
    abstract fun bindCityRemoteRepoMapper(mapper: CityRemoteRepoMapper): Mapper<
            @JvmSuppressWildcards List<ProvinceRemoteModel>,
            @JvmSuppressWildcards List<ProvinceRepoModel>
            >
    @Binds
    abstract fun bindCheckVersionRemoteRepoMapper(mapper: CheckVersionRemoteRepoMapper): Mapper<
            CheckVersionRemoteModel,
            CheckVersionRepoModel
            >

    @Binds
    abstract fun bindConfigRemoteRepoMapper(mapper: ConfigRemoteRepoMapper): Mapper<
            ConfigRemoteModel,
            ConfigRepoModel
            >
    @Binds
    abstract fun commonRepo(dev: CommonRepositoryImpl): CommonRepository

    @Binds
    abstract fun bindCityUseCase(useCase: CityUseCase): UseCase<Unit,  @JvmSuppressWildcards List<ProvinceRepoModel>>


    @Binds
    abstract fun bindCheckVersionUseCase(useCase: CheckVersionUseCase): UseCase<String,  CheckVersionRepoModel>


    @Binds
    abstract fun bindGetConfigUseCase(useCase: GetConfigUseCase): UseCase<Unit, ConfigRepoModel>

    @Binds
    abstract fun bindGetAppLinkUseCase(useCase: GetAppLinkUseCase): UseCase<Unit, GetAppLinkRepoModel>
}