package com.paya.domain.models.repo

data class ProvinceRepoModel(
    val name: String,
    val cities: List<City>
)

data class City(
    val name: String
)