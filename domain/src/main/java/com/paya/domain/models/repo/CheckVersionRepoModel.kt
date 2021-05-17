package com.paya.domain.models.repo

data class CheckVersionRepoModel(
    val isUpdate: Boolean,
    val isForce: Boolean,
    val link: String

)