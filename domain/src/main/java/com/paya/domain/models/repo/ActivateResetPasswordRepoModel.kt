package com.paya.domain.models.repo

data class ActivateResetPasswordRepoModel(
	val username: String,
	val code: String
)