package com.paya.domain.models.remote

data class ProvinceRemoteModel(
    val name: String,
    val cities: List<City>
)

data class City(
    val name: String
)