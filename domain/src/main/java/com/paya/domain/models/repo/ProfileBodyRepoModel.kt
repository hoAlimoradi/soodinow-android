package com.paya.domain.models.repo

data class ProfileBodyRepoModel(
	val firstName: String,
	val lastName: String,
	val phone: String,
	val email: String,
	val personalCode: String,
	val birthDay: String,
	val bban: String,
	val gender: String,
	val state: String,
	val city: String,
	val address: String
)