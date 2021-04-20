package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.ProvinceRemoteModel
import com.paya.domain.models.repo.City
import com.paya.domain.models.repo.ProvinceRepoModel
import javax.inject.Inject

class CityRemoteRepoMapper @Inject constructor() : Mapper<
        @JvmSuppressWildcards List<ProvinceRemoteModel>,
        @JvmSuppressWildcards List<ProvinceRepoModel>
        > {
    override fun map(param: List<ProvinceRemoteModel>): List<ProvinceRepoModel> =
        param.map { province ->
            ProvinceRepoModel(
                province.name,
                province.cities.map { city -> City(city.name) }
            )
        }
}