package com.paya.domain.models.repo


data class ChangePasswordRepoBodyModel(
	val oldPassword: String,
	val password: String,
)