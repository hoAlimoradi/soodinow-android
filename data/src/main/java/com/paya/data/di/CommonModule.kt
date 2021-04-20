package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.mapper.CityRemoteRepoMapper
import com.paya.data.repository.CommonRepositoryImpl
import com.paya.domain.models.remote.ProvinceRemoteModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.common.CityUseCase
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
    abstract fun commonRepo(dev: CommonRepositoryImpl): CommonRepository

    @Binds
    abstract fun bindCityUseCase(useCase: CityUseCase): UseCase<Unit,  @JvmSuppressWildcards List<ProvinceRepoModel>>


}