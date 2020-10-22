package com.paya.domain.models.repo

data class UserInfoRepoModel(
	val accessToken: String? = "",
	val isPasswordSet: Boolean = false,
	val isHintShowed: Boolean = false
)