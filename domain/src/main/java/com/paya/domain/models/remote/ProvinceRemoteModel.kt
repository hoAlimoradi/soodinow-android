package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class ProvinceRemoteModel(
    val name: String?,
    val cities: List<City>?
): NoObfuscate

data class City(
    val name: String?
): NoObfuscate