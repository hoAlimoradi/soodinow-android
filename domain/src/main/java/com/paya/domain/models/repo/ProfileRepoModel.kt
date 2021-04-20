package com.paya.domain.models.repo

data class ProfileRepoModel(
	val id:Int,
	val name: String,
	val personalCode: String,
	val birthDay: String,
	val bban: String,
	val complete: Boolean,
	val mobile: String?,
	val gender: String,
	val state: String,
	val city: String,
	val address: String
	
)