package com.paya.domain.models.repo

data class ActivateRepoModel(
	val username: String,
	val phoneNumber: String,
	val code: String
)