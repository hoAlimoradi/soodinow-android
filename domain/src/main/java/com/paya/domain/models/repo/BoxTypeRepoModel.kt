package com.paya.domain.models.repo

data class BoxTypeRepoModel(
    val types: List<BoxTypeParam>
)

data class BoxTypeParam(val type: String, val name: String)